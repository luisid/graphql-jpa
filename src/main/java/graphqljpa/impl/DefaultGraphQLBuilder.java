package graphqljpa.impl;

import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphqljpa.builders.GraphQLField;
import graphqljpa.builders.GraphQLObject;
import graphqljpa.schema.*;
import graphqljpa.schema.metadata.GraphQLAttributeMetadata;
import graphqljpa.schema.metadata.GraphQLManagedTypeMetadata;
import graphqljpa.schema.metadata.GraphQLMetaData;

public class DefaultGraphQLBuilder implements GraphQLBuilder {
    @Override
    public GraphQLObjectType buildObjectType(GraphQLManagedTypeMetadata metaData) {
        return GraphQLObjectType.newObject()
                .name(metaData.getName())
                .description(metaData.getDescription())
                //.withInterfaces()
                //.withDirectives()
                //.fields()
                .build();
    }

    @Override
    public GraphQLFieldDefinition buildField(GraphQLAttributeMetadata metaData) {
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(metaData.getName())
                .description(metaData.getDescription())
                .deprecate(metaData.getDeprecationReason())
                .type(metaData.getType())
                //.argument()
                .build();
        // check output type. Could be scalar, objectType/interface/union
        // directive by the builder
        // arguments - kind of complicated
    }


}
