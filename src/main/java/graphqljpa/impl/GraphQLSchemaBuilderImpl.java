package graphqljpa.impl;

import graphql.schema.*;
import graphqljpa.impl.metadata.GraphQLMetadataFactory;
import graphqljpa.schema.GraphQLBuilder;
import graphqljpa.schema.GraphQLExtension;
import graphqljpa.schema.GraphQLSchemaBuilder;
import graphqljpa.schema.metadata.*;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.MappedSuperclassType;
import javax.persistence.metamodel.Metamodel;
import java.util.*;
import java.util.stream.Collectors;

public class GraphQLSchemaBuilderImpl implements GraphQLSchemaBuilder {
    private String description;
    private EntityManager entityManager;
    private Set<GraphQLExtension> extensions = new HashSet<>();
    private Set<GraphQLBuilder> builders = new HashSet<>();
    private GraphQLBuilder builder = new DefaultGraphQLBuilder();

    public GraphQLSchemaBuilderImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
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

        GraphQLObjectType rootQuery = GraphQLObjectType.newObject()
                .description(description)
                .name("RootType")
                .fields(this.buildRootFields(rootObjectTypes))
                .build();

        return GraphQLSchema.newSchema()
                .additionalTypes(allObjectTypes)
                .query(rootQuery)
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


    private List<GraphQLFieldDefinition> buildRootFields(List<GraphQLObjectType> rootTypes) {
        return rootTypes
                .stream()
                .map(this::buildRootField)
                .collect(Collectors.toList());
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

    private List<GraphQLDirective> buildDirectives(GraphQLAttributeMetadata metaData) {
        return new ArrayList<>();
    }

    private List<GraphQLDirective> buildDirectives(GraphQLManagedTypeMetadata metaData) {
        return new ArrayList<>();
    }

    private GraphQLFieldDefinition buildRootField(GraphQLObjectType rootObject) {
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(rootObject.getName())
                .description(rootObject.getDescription())
                .type(GraphQLTypeReference.typeRef(rootObject.getName()))
                .build();
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

        List<GraphQLDirective> directives = this.buildDirectives(metaData);

        directives.forEach(directive -> fieldBuilder.withDirectives(directive));

        //TODO Weird logic to combine data fetchers

        return fieldBuilder.build();
    }

    private List<GraphQLFieldDefinition> buildFields(GraphQLManagedTypeMetadata metaData) {
        return metaData.getAttributes()
                .stream()
                .filter(this::isNotIgnored)
                .map(this::buildField)
                .collect(Collectors.toList());

    }

    private void buildInterfaces() {

    }
//    @Override
//    public GraphQLSchema build() {
//        if (queryBuilder == null) {
//            queryBuilder = new GraphQLQueryBuilderImpl();
//        }
//
//        GraphQLObjectType rootType = queryBuilder.build();
//
//        return GraphQLSchema.newSchema().query(rootType).build();
//    }
}
