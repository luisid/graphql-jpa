package scenario;

import graphql.ExceptionWhileDataFetching;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.validation.ValidationError;
import graphqljpa.impl.GraphQLSchemaBuilderImpl;
import graphqljpa.impl.extensions.authorization.Authorization;
import graphqljpa.impl.extensions.authorization.AuthorizationContext;
import graphqljpa.impl.extensions.authorization.Role;
import graphqljpa.schema.GraphQLExtension;
import graphqljpa.schema.GraphQLSchemaBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import static graphql.validation.ValidationErrorType.FieldUndefined;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class Full {
    private EntityManagerFactory entityManagerFactory;
    private GraphQLSchema schema;

    @Before
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory( "star-wars-full" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        GraphQLSchemaBuilder builder = new GraphQLSchemaBuilderImpl(entityManager);
        /**
         * Set up extensions
         */
        Set<GraphQLExtension> extensions = new HashSet<>();
        extensions.add(new Authorization());
        /**
         * Add extension Authorization for the whole schema
         */
        builder.extensions(extensions);
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

        AuthorizationContext context = new AuthorizationContext() {
            @Override
            public Role getRole() {
                return Role.webmaster;
            }
        };

        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .context(context)
                .query(query)
                .build();

        ExecutionResult result = graphQL.execute(executionInput);
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

        AuthorizationContext context = new AuthorizationContext() {
            @Override
            public Role getRole() {
                return Role.webmaster;
            }
        };

        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .context(context)
                .query(query)
                .build();

        ExecutionResult result = graphQL.execute(query);
        /**
         * Should have 2 droids
         */
        assertEquals(((List) ((LinkedHashMap) result.getData()).get("Droids")).size(), 2);
    }

    @Test
    public void shouldHaveNotAllowedErrorAndAppearsInShouldBeNull() {
        GraphQL graphQL = GraphQL.newGraphQL(schema).build();

        String query = "query HeroNameQuery {" +
                "Droids {" +
                "name\nappearsIn\nfriends {name}" +
                "}" +
                "}";

        AuthorizationContext context = new AuthorizationContext() {
            @Override
            public Role getRole() {
                return Role.user;
            }
        };

        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .context(context)
                .query(query)
                .build();

        ExecutionResult result = graphQL.execute(executionInput);
        /**
         * Check not allowed error
         */
        assertEquals(
                ((ExceptionWhileDataFetching) (result.getErrors().get(0))).getException().getMessage(),
                "Not allowed: role 'user' has not permission to access this field"
        );

        /**
         * Data Should return but appearsIn should be null
         */
        assertNull(((LinkedHashMap)(((List) ((LinkedHashMap) result.getData()).get("Droids")).get(0))).get("appearsIn"));
    }
}
