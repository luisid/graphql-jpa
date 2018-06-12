package graphqljpa.schema;

import graphqljpa.builders.GraphQLDirective;
import graphqljpa.schema.metadata.GraphQLMetaData;

import java.util.Set;

public interface GraphQLExtension {
    Set<GraphQLDirective> getDirectives(GraphQLMetaData metaData);
}
