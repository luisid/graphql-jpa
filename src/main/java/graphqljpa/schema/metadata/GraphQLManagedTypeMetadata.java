package graphqljpa.schema.metadata;

import graphql.schema.GraphQLArgument;
import graphqljpa.schema.GraphQLBuilder;

import java.util.List;
import java.util.Set;

public interface GraphQLManagedTypeMetadata extends GraphQLMetaData {
    boolean isRoot();
    boolean isBasic();
    boolean isEntity();
    boolean isEmbeddable();
    boolean isMappedSuperClass();
    Set<GraphQLAttributeMetadata> getAttributes();
    List<GraphQLArgument> getArguments();
    GraphQLAttributeMetadata getAttribute(String name);
    Class getJavaType();
}
