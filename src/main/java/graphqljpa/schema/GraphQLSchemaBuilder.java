package graphqljpa.schema;

import graphql.schema.GraphQLSchema;

import java.util.Set;

/**
 * Performance improvement.
 * - Detect circular queries. If there are circular query a good way to improve performance is to use Data Loaders
 * - Use joins only when needed. Maybe doing multiple sql query is faster than doing joins (Investigate)
 * - Inspect the query AST to try to find performance improvement that can be may. (Plugins for data fetching process?)
 * -
 */
public interface GraphQLSchemaBuilder {
    /**
     * Set description for the query root GraphQLObjectType
     * @param description String
     * @return GraphQLSchemaBuilder
     */
    GraphQLSchemaBuilder description(String description);

    /**
     *
     * @param extensions Set<GraphQLExtension>
     * @return GraphQLSchemaBuilder
     */
    GraphQLSchemaBuilder extensions(Set<GraphQLExtension> extensions);

    /**
     *
     * @param builder GraphQLBuilder
     * @return GraphQLSchemaBuilder
     */
    GraphQLSchemaBuilder builder(GraphQLBuilder builder);

    /**
     *
     * @param builders Set<GraphQLBuilder>
     * @return GraphQLSchemaBuilder
     */
    GraphQLSchemaBuilder builders(Set<GraphQLBuilder> builders);

    /**
     * Create graphql schema
     * @return GraphQLSchema
     */
    GraphQLSchema build();
}
