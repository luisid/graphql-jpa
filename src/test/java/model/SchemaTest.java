package model;

import graphql.schema.GraphQLSchema;
import graphqljpa.impl.GraphQLSchemaBuilderImpl;
import graphqljpa.schema.GraphQLSchemaBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import static org.junit.Assert.assertNotNull;

public class SchemaTest {
    private EntityManagerFactory entityManagerFactory;
    private GraphQLSchema schema;

    @Before
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory( "star-wars" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        GraphQLSchemaBuilder builder = new GraphQLSchemaBuilderImpl(entityManager);
        schema = builder.build();
    }

    @After
    public void destroy() {
        entityManagerFactory.close();
    }

    @Test
    public void schemaHasHumanType() {
        assertNotNull(schema.getAdditionalTypes().stream().filter(t -> t.getName().equals("Human")).findFirst());
    }

    @Test
    public void schemaHasDroidType() {
        assertNotNull(schema.getAdditionalTypes().stream().filter(t -> t.getName().equals("Droid")).findFirst());
    }

    @Test
    public void schemaHasCharacterType() {
        assertNotNull(schema.getAdditionalTypes().stream().filter(t -> t.getName().equals("Character")).findFirst());
    }
}
