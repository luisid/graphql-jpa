package scenario;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.validation.ValidationError;
import graphqljpa.impl.GraphQLSchemaBuilderImpl;
import graphqljpa.schema.GraphQLSchemaBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.LinkedHashMap;
import java.util.List;

import static graphql.validation.ValidationErrorType.FieldUndefined;
import static org.junit.Assert.assertEquals;

public class Simple {
    private EntityManagerFactory entityManagerFactory;
    private GraphQLSchema schema;

    @Before
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory( "star-wars-simple" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        GraphQLSchemaBuilder builder = new GraphQLSchemaBuilderImpl(entityManager);
        schema = builder.build();
    }

    @After
    public void destroy() {
        entityManagerFactory.close();
    }

    @Test
    public void shouldBeFieldUndefined() {
        GraphQL graphQL = GraphQL.newGraphQL(schema).build();

        String query = "query HeroNameQuery {" +
                "Droids {" +
                "name\nprimaryFunction\nfriends { name}" +
                "}" +
                "}";

        ExecutionResult result = graphQL.execute(query);
        /**
         * primaryFunction do not exist in this GraphQL schema because GraphQLIgnore was used.
         * an error is expected of type FieldUndefined
         */
        assertEquals(((ValidationError) result.getErrors().get(0)).getValidationErrorType(), FieldUndefined);
    }

    @Test
    public void shouldQueryWithoutPrimaryFunction() {
        GraphQL graphQL = GraphQL.newGraphQL(schema).build();

        String query = "query HeroNameQuery {" +
                "Droids {" +
                "name\nappearsIn\nfriends { name}" +
                "}" +
                "}";

        ExecutionResult result = graphQL.execute(query);
        /**
         * Should have 2 droids
         */
        assertEquals(((List) ((LinkedHashMap) result.getData()).get("Droids")).size(), 2);
    }
}
