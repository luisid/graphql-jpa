package graphqljpa.schema.metadata;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLDirective;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLOutputType;
import graphqljpa.schema.GraphQLBuilder;

import java.lang.annotation.Annotation;
import java.util.Set;

public interface GraphQLMetaData {
    Annotation getAnnotation(Class<? extends Annotation> clazz);
    String getName();
    String getDescription();
    boolean isIgnored();
    GraphQLBuilder getBuilder();
    Set<GraphQLBuilder> getBuilders();
}
