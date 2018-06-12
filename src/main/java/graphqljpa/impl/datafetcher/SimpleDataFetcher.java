package graphqljpa.impl.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLArgument;
import graphqljpa.schema.metadata.GraphQLIdentifiableTypeMetadata;
import graphqljpa.schema.metadata.GraphQLManagedTypeMetadata;

import javax.persistence.EntityManager;

public class SimpleDataFetcher implements DataFetcher {
    private EntityManager entityManager;
    private GraphQLManagedTypeMetadata metadata;

    public SimpleDataFetcher(EntityManager entityManager, GraphQLManagedTypeMetadata metadata) {
        this.entityManager = entityManager;
        this.metadata = metadata;
    }


    @Override
    public Object get(DataFetchingEnvironment environment) {
        entityManager.find(metadata.getJavaType(), 1);
    }
}
