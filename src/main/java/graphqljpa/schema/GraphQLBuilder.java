package graphqljpa.schema;

import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphqljpa.schema.metadata.GraphQLAttributeMetadata;
import graphqljpa.schema.metadata.GraphQLManagedTypeMetadata;

public interface GraphQLBuilder {
    GraphQLObjectType buildObjectType(GraphQLManagedTypeMetadata metaData);
    GraphQLFieldDefinition buildField(GraphQLAttributeMetadata metaData);
}
