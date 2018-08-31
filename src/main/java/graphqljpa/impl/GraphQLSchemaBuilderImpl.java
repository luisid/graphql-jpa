package graphqljpa.impl;

import graphql.GraphQLException;
import graphql.schema.*;
import graphqljpa.impl.builders.GraphQLBuilderImpl;
import graphqljpa.impl.datafetcher.BaseDataFetcher;
import graphqljpa.impl.metadata.GraphQLMetadataFactory;
import graphqljpa.schema.*;
import graphqljpa.schema.metadata.*;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.MappedSuperclassType;
import javax.persistence.metamodel.Metamodel;
import java.util.*;
import java.util.stream.Collectors;

public class GraphQLSchemaBuilderImpl implements GraphQLSchemaBuilder {
    static final public String idArgument = "id";
    private String description;
    private EntityManager entityManager;
    private NamingStrategy namingStrategy = new NamingStrategy() {};
    private Set<GraphQLExtension> extensions = new HashSet<>();
    private Set<GraphQLBuilder> builders = new HashSet<>();
    private GraphQLBuilder builder = new GraphQLBuilderImpl();
    private GraphQLRootObjectBuilder rootObjectBuilder;
    private GraphQLPagination graphQLPagination = new GraphQLPaginationImpl();

    public GraphQLSchemaBuilderImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        graphQLPagination = new GraphQLPaginationImpl();
        graphQLPagination.entityManager(entityManager);
        rootObjectBuilder = new GraphQLRootObjectBuilder(entityManager, namingStrategy, graphQLPagination);
    }

    @Override
    public GraphQLSchemaBuilder namingStrategy(NamingStrategy namingStrategy) {
        this.namingStrategy = namingStrategy;
        rootObjectBuilder = new GraphQLRootObjectBuilder(entityManager, namingStrategy, graphQLPagination);
        return this;
    }

    @Override
    public GraphQLSchemaBuilder description(String description) {
        this.description = description;
        return this;
    }

    @Override
    public GraphQLSchemaBuilder extensions(Set<GraphQLExtension> extensions) {
        this.extensions.addAll(extensions);
        return this;
    }

    @Override
    public GraphQLSchemaBuilder builder(GraphQLBuilder builder) {
        this.builder = builder;
        return this;
    }

    @Override
    public GraphQLSchemaBuilder builders(Set<GraphQLBuilder> builders) {
        this.builders = builders;
        return this;
    }

    @Override
    public GraphQLSchema build() {
        Metamodel metamodel = entityManager.getMetamodel();
        Map<String, GraphQLEntityTypeMetadata> entitiesMap = metamodel.getEntities()
                .stream()
                .map(this::getMetaData)
                .filter(this::isNotIgnored)
                .collect(Collectors.toMap(element -> element.getName(), element -> element));

        Map<String, GraphQLEmbeddableTypeMetadata> embeddablesMap = metamodel.getEmbeddables()
                .stream()
                .map(this::getMetaData)
                .filter(this::isNotIgnored)
                .collect(Collectors.toMap(element -> element.getName(), element -> element));

        List<GraphQLObjectType> rootObjectTypes = entitiesMap.values().stream()
                .filter(this::isRoot)
                .map(this::buildObjectType)
                .collect(Collectors.toList());

        List<GraphQLObjectType> objectTypes = entitiesMap.values().stream()
                .filter(this::isNotRoot)
                .map(this::buildObjectType)
                .collect(Collectors.toList());

        List<GraphQLObjectType> embeddableTypes = embeddablesMap.values().stream()
                .map(this::buildObjectType)
                .collect(Collectors.toList());

        Set<GraphQLType> allObjectTypes = new HashSet<>();

        allObjectTypes.addAll(objectTypes);
        allObjectTypes.addAll(rootObjectTypes);
        allObjectTypes.addAll(embeddableTypes);
        allObjectTypes.addAll(graphQLPagination.getTypes());

        List<GraphQLFieldDefinition> fields = rootObjectBuilder.buildRootFields(rootObjectTypes);

        GraphQLObjectType rootQuery = GraphQLObjectType.newObject()
                .description(description)
                .name("RootType")
                .fields(fields)
                .build();

        return GraphQLSchema.newSchema()
                .additionalTypes(allObjectTypes)
                .query(rootQuery)
                //.mutation(null)
                .build();
    }

    private GraphQLEntityTypeMetadata getMetaData(EntityType entityType) {
        return GraphQLMetadataFactory.getMetaData(entityType);
    }

    private GraphQLEmbeddableTypeMetadata getMetaData(EmbeddableType embeddableType) {
        return GraphQLMetadataFactory.getMetaData(embeddableType);
    }

    private GraphQLMappedSuperclassTypeMetadata getMetaData(MappedSuperclassType mappedSuperclassType) {
        return GraphQLMetadataFactory.getMetaData(mappedSuperclassType);
    }

    private Boolean isNotIgnored(GraphQLMetaData metaData) {
        return !metaData.isIgnored();
    }

    private Boolean isNotRoot(GraphQLIdentifiableTypeMetadata metaData) {
        return !metaData.isRoot();
    }

    private Boolean isRoot(GraphQLIdentifiableTypeMetadata metaData) {
        return metaData.isRoot();
    }

    private GraphQLObjectType buildObjectType(GraphQLManagedTypeMetadata metaData) {
        GraphQLBuilder builder = metaData.getBuilder();
        Set<GraphQLBuilder> builders = metaData.getBuilders();

        if (builder == null) {
            builder = this.builder;
        }

        builders.addAll(this.builders);
        GraphQLObjectType objectType = builder.buildObjectType(metaData);
        GraphQLObjectType.Builder objectBuilder = GraphQLObjectType.newObject();
        objectType.getInterfaces().forEach(type -> {
            if (type instanceof GraphQLTypeReference) {
                objectBuilder.withInterface((GraphQLTypeReference) type);
            } else if (type instanceof GraphQLInterfaceType) {
                objectBuilder.withInterface((GraphQLInterfaceType) type);
            } else {
                throw new Error("Unable to merge GraphQLObjectType");
            }
        });
        objectType.getDirectives().forEach(objectBuilder::withDirectives);
        objectBuilder
                .name(objectType.getName())
                .description(objectType.getDescription())
                .fields(objectType.getFieldDefinitions());


        List<GraphQLFieldDefinition> fields = this.buildFields(metaData);
        objectBuilder.fields(fields);

        // TODO: Refactor code. It is too ugly

        builders.stream()
                .map(b -> b.buildObjectType(metaData))
                .forEach(o -> {
                    objectBuilder.fields(o.getFieldDefinitions());
                    o.getDirectives().forEach(objectBuilder::withDirectives);
                    o.getInterfaces().forEach(type -> {
                        if (type instanceof GraphQLTypeReference) {
                            objectBuilder.withInterface((GraphQLTypeReference) type);
                        } else if (type instanceof GraphQLInterfaceType) {
                            objectBuilder.withInterface((GraphQLInterfaceType) type);
                        } else {
                            throw new Error("Unable to merge GraphQLObjectType");
                        }
                    });
                });

        return objectBuilder.build();
    }

    private List<GraphQLExtension> getExtensionsForField(GraphQLAttributeMetadata metaData) {
        List<GraphQLExtension> extensions = new ArrayList<>(this.extensions);
        extensions.addAll(metaData.getExtensions());
        return extensions;
    }

    private DataFetcher<?> buildDataFetcher(DataFetcher originalDataFetcher, GraphQLAttributeMetadata metaData, List<GraphQLExtension> extensions) {
        DataFetcher dataFetcher = (DataFetchingEnvironment environment) -> {
            for (int i = 0; i < extensions.size(); i++) {
                if (!extensions.get(i).next(environment, metaData)) {
                    throw new GraphQLException("Execution interrupted by extension");
                }
            }
            return originalDataFetcher.get(environment);
        };

        return dataFetcher;
    }

    private GraphQLFieldDefinition buildField(GraphQLAttributeMetadata metaData) {
        GraphQLBuilder builder = metaData.getBuilder();

        if (builder == null) {
            builder = this.builder;
        }

        GraphQLFieldDefinition fieldDefinition = builder.buildField(metaData);
        GraphQLFieldDefinition.Builder fieldBuilder = GraphQLFieldDefinition.newFieldDefinition()
                .name(fieldDefinition.getName())
                .description(fieldDefinition.getDescription())
                .deprecate(fieldDefinition.getDeprecationReason())
                .type(fieldDefinition.getType());

        fieldDefinition.getArguments().forEach(argument -> fieldBuilder.argument(argument));
        fieldDefinition.getDirectives().forEach(directive -> fieldBuilder.withDirectives(directive));


        List<GraphQLExtension> extensions = this.getExtensionsForField(metaData);

        fieldBuilder.dataFetcher(this.buildDataFetcher(fieldDefinition.getDataFetcher(), metaData, extensions));

        return fieldBuilder.build();
    }

    private List<GraphQLFieldDefinition> buildFields(GraphQLManagedTypeMetadata metaData) {
        return metaData.getAttributes()
                .stream()
                .filter(this::isNotIgnored)
                .map(this::buildField)
                .collect(Collectors.toList());

    }
}
