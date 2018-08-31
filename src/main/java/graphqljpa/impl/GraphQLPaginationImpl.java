package graphqljpa.impl;

import graphql.schema.*;
import graphqljpa.impl.datafetcher.BaseDataFetcher;
import graphqljpa.impl.metadata.GraphQLMetadataFactory;
import graphqljpa.schema.GraphQLPagination;
import graphqljpa.schema.NamingStrategy;
import graphqljpa.schema.metadata.GraphQLEntityTypeMetadata;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import graphql.Scalars;

public class GraphQLPaginationImpl implements GraphQLPagination {
    private EntityManager entityManager;

    @Override
    public GraphQLPagination entityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        return this;
    }

    @Override
    public List<GraphQLType> getTypes() {
        return new ArrayList<>();
    }

    @Override
    public GraphQLFieldDefinition buildField(GraphQLEntityTypeMetadata entityTypeMetadata, GraphQLObjectType objectType) {

        return GraphQLFieldDefinition.newFieldDefinition()
                .name(entityTypeMetadata.getName()) // Will be ignore
                .type(GraphQLList.list(GraphQLTypeReference.typeRef(objectType.getName())))
                .dataFetcher(new BaseDataFetcher(
                        entityManager,
                        entityTypeMetadata
                ))
                .build();
    }
}
