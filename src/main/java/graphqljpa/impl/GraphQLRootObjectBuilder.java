package graphqljpa.impl;

import graphql.schema.*;
import graphqljpa.impl.arguments.ArgumentUtils;
import graphqljpa.impl.datafetcher.BaseDataFetcher;
import graphqljpa.impl.datafetcher.SimpleDataFetcher;
import graphqljpa.impl.metadata.GraphQLMetadataFactory;
import graphqljpa.schema.GraphQLPagination;
import graphqljpa.schema.NamingStrategy;
import graphqljpa.schema.metadata.GraphQLEntityTypeMetadata;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

public class GraphQLRootObjectBuilder {
    private NamingStrategy namingStrategy;
    private EntityManager entityManager;
    private GraphQLPagination pagination;

    GraphQLRootObjectBuilder(EntityManager entityManager, NamingStrategy namingStrategy, GraphQLPagination pagination) {
        this.entityManager = entityManager;
        this.namingStrategy = namingStrategy;
        this.pagination = pagination;
    }
    public List<GraphQLFieldDefinition> buildRootFields(List<GraphQLObjectType> rootTypes) {
        List<GraphQLFieldDefinition> fields = rootTypes
                .stream()
                .map(this::buildSingleRootField)
                .collect(Collectors.toList());

        fields.addAll(
                rootTypes
                        .stream()
                        .map(this::buildPluralRootField)
                        .collect(Collectors.toList())
        );

        return fields;
    }
    private GraphQLFieldDefinition buildSingleRootField(GraphQLObjectType rootObject) {
        GraphQLEntityTypeMetadata rootMetadata = (GraphQLEntityTypeMetadata) GraphQLMetadataFactory.getMetadataFromObjectType(rootObject);


        return GraphQLFieldDefinition.newFieldDefinition()
                .name(namingStrategy.singularize(rootObject.getName()))
                .description(rootObject.getDescription())
                .type(GraphQLTypeReference.typeRef(rootObject.getName()))
                .argument(ArgumentUtils.getArgumentFromAttribute(rootMetadata.getId(), true, "id"))
                .dataFetcher(new SimpleDataFetcher(
                        entityManager,
                        (GraphQLEntityTypeMetadata) GraphQLMetadataFactory.getMetadataFromObjectType(rootObject)
                ))
                .build();
    }

    private GraphQLFieldDefinition buildPluralRootField(GraphQLObjectType rootObject) {
        GraphQLEntityTypeMetadata rootMetadata = (
                (GraphQLEntityTypeMetadata) GraphQLMetadataFactory.getMetadataFromObjectType(rootObject)
        );
        GraphQLFieldDefinition paginationField = pagination.buildField(rootMetadata, rootObject);
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(namingStrategy.pluralize(rootObject.getName()))
                .description(rootObject.getDescription())
                .type(paginationField.getType())
                .dataFetcher(paginationField.getDataFetcher())
                .argument(paginationField.getArguments())
                .dataFetcher(paginationField.getDataFetcher())
                .build();
    }
}
