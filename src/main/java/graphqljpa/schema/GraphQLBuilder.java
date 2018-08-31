package graphqljpa.schema;

import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphqljpa.schema.metadata.GraphQLAttributeMetadata;
import graphqljpa.schema.metadata.GraphQLManagedTypeMetadata;

import javax.persistence.EntityManager;

public interface GraphQLBuilder {
    /**
     * Set a entityManager.
     * @param entityManager
     * @return GraphQLBuilder
     */
    GraphQLBuilder entityManager(EntityManager entityManager);

    /**
     * build a ObjectType using the metadata.
     * @param metaData
     * @return GraphQLObjectType
     */
    GraphQLObjectType buildObjectType(GraphQLManagedTypeMetadata metaData);

    /**
     * build a fieldDefinition using the attribute metadata.
     * @param metaData
     * @return GraphQLFieldDefinition
     */
    GraphQLFieldDefinition buildField(GraphQLAttributeMetadata metaData);
}
