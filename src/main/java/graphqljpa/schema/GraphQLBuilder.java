package graphqljpa.schema;

import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphqljpa.builders.GraphQLField;
import graphqljpa.builders.GraphQLObject;
import graphqljpa.schema.metadata.GraphQLAttributeMetadata;
import graphqljpa.schema.metadata.GraphQLManagedTypeMetadata;
import graphqljpa.schema.metadata.GraphQLMetaData;

public interface GraphQLBuilder {
    GraphQLObjectType buildObjectType(GraphQLManagedTypeMetadata metaData);
    GraphQLFieldDefinition buildField(GraphQLAttributeMetadata metaData);
}
