package model;

import graphql.ExecutionResult;
import graphql.schema.GraphQLSchema;
import graphqljpa.impl.GraphQLSchemaBuilderImpl;
import graphqljpa.schema.GraphQLSchemaBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;

public class SchemaTest {
    private EntityManagerFactory entityManagerFactory;

    @Before
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory( "star-wars" );
    }

    @After
    public void destroy() {
        entityManagerFactory.close();
    }

    @Test
    public void schemaCreation() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        GraphQLSchemaBuilder builder = new GraphQLSchemaBuilderImpl(entityManager);
        GraphQLSchema schema = builder.build();

        return;
    }
}
