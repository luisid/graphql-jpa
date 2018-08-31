package graphqljpa.schema;

import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLType;
import graphqljpa.schema.metadata.GraphQLEntityTypeMetadata;

import javax.persistence.EntityManager;
import java.util.List;

public interface GraphQLPagination {
    /**
     * Set the entityManager
     * @param entityManager
     * @return GraphQLPagination
     */
    GraphQLPagination entityManager(EntityManager entityManager);

    /**
     * Return a GraphQLFieldDefinition
     * Should be used to add pagination support using the entityTypeMetadata and objectType.
     * @param entityTypeMetadata
     * @param objectType
     * @return
     */
    GraphQLFieldDefinition buildField(GraphQLEntityTypeMetadata entityTypeMetadata, GraphQLObjectType objectType);

    /**
     * Get all types used by the implementation to be added to the Schema
     * @return List<GraphQLType>
     */
    List<GraphQLType> getTypes();
}
