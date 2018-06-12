package graphqljpa.schema.metadata;

import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLOutputType;

import java.util.List;

public interface GraphQLAttributeMetadata extends GraphQLMetaData {
    GraphQLManagedTypeMetadata getDeclaringType();
    String getDeprecationReason();
    GraphQLOutputType getType();
    boolean isBasic();
    boolean isManyToMany();
    boolean isOneToOne();
    boolean isManyToOne();
    boolean isOneToMany();
    boolean isElementCollection();
    boolean isEmbedded();
    boolean isAssociation();
    boolean isCollection();
}
