package graphqljpa.impl.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphqljpa.impl.GraphQLSchemaBuilderImpl;
import graphqljpa.schema.metadata.GraphQLEntityTypeMetadata;
import graphqljpa.schema.metadata.GraphQLManagedTypeMetadata;

import javax.persistence.EntityManager;

public class SimpleDataFetcher implements DataFetcher {
    private EntityManager entityManager;
    private GraphQLEntityTypeMetadata metadata;

    public SimpleDataFetcher(EntityManager entityManager, GraphQLEntityTypeMetadata metadata) {
        this.entityManager = entityManager;
        this.metadata = metadata;
    }

    @Override
    public Object get(DataFetchingEnvironment environment) {
        Object id = environment.getArgument(GraphQLSchemaBuilderImpl.idArgument);
        return entityManager.find(metadata.getEntityType().getClass(), id);
    }
}
