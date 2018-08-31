package graphqljpa.schema.metadata;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLDirective;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLOutputType;
import graphqljpa.schema.GraphQLBuilder;
import graphqljpa.schema.GraphQLExtension;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

public interface GraphQLMetaData {
    /**
     * Obtain an annotation if it matches the class passed as a parameter.
     * @param clazz
     * @return String
     */
    Annotation getAnnotation(Class<? extends Annotation> clazz);

    /**
     * Standarize getName() for all metadata.
     * @return String
     */
    String getName();

    /**
     * Return the original class or property name.
     * @return String
     */
    String getOriginalName();

    /**
     * Return a description if provided by GraphQLDescription
     * @return String
     */
    String getDescription();

    /**
     * @return boolean
     */
    boolean isIgnored();
    GraphQLBuilder getBuilder();
    Set<GraphQLBuilder> getBuilders();
    List<GraphQLExtension> getExtensions();
}
