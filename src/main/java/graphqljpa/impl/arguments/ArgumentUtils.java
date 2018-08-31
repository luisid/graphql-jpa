package graphqljpa.impl.arguments;

import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLInputType;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLType;
import graphqljpa.schema.metadata.GraphQLAttributeMetadata;

public class ArgumentUtils {
    static public GraphQLArgument getArgumentFromAttribute(GraphQLAttributeMetadata attributeMetadata, boolean nullable) {
        return ArgumentUtils.getArgumentFromAttribute(attributeMetadata, nullable, attributeMetadata.getName());
    }

    static public GraphQLArgument getArgumentFromAttribute(GraphQLAttributeMetadata attributeMetadata, boolean nonNull, String name) {
        GraphQLType type = attributeMetadata.getType();

        if (nonNull){
            type = new GraphQLNonNull(type);
        }

        return GraphQLArgument.newArgument()
                .name(name)
                .description(attributeMetadata.getArgumentDescription())
                .type((GraphQLInputType) type)
                .build();
    }
}
