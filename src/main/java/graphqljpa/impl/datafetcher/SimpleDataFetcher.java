package graphqljpa.impl.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphqljpa.schema.metadata.GraphQLManagedTypeMetadata;

import javax.persistence.EntityManager;

public class SimpleDataFetcher implements DataFetcher {
    private EntityManager entityManager;
    private GraphQLManagedTypeMetadata metadata;
    private String idArgument = "id";

    public SimpleDataFetcher(EntityManager entityManager, GraphQLManagedTypeMetadata metadata) {
        this.entityManager = entityManager;
        this.metadata = metadata;
    }

    public SimpleDataFetcher(EntityManager entityManager, GraphQLManagedTypeMetadata metadata, String idArgument) {
        this(entityManager, metadata);
        this.idArgument = idArgument;
    }


    @Override
    public Object get(DataFetchingEnvironment environment) {
        Object id = environment.getArgument(idArgument);
        entityManager.find(metadata.getJavaType(), id);
        return null;
    }
}
