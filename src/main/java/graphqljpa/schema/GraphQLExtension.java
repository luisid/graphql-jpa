package graphqljpa.schema;

import graphql.schema.GraphQLDirective;
import graphqljpa.schema.metadata.GraphQLMetaData;

import java.util.Set;

public interface GraphQLExtension {
    Set<GraphQLDirective> getDirectives(GraphQLMetaData metaData);
}
