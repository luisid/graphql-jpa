package scenario;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphqljpa.impl.GraphQLSchemaBuilderImpl;
import graphqljpa.schema.GraphQLSchemaBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import static org.junit.Assert.*;
import java.util.LinkedHashMap;
import java.util.List;

public class Basic {
    private EntityManagerFactory entityManagerFactory;
    private GraphQLSchema schema;

    @Before
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory( "star-wars-basic" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        GraphQLSchemaBuilder builder = new GraphQLSchemaBuilderImpl(entityManager);
        schema = builder.build();
    }

    @After
    public void destroy() {
        entityManagerFactory.close();
    }

    @Test
    public void query() {
        GraphQL graphQL = GraphQL.newGraphQL(schema).build();

        String query = "query HeroNameQuery {" +
                "Droids {" +
                "name\nprimaryFunction\nfriends { name}" +
                "}" +
                "}";

        ExecutionResult result = graphQL.execute(query);
        /**
         * Should have 2 droids
         */
        assertEquals(((List) ((LinkedHashMap) result.getData()).get("Droids")).size(), 2);
    }
}
