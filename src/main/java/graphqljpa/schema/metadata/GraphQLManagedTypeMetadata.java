package graphqljpa.schema.metadata;

import graphql.schema.GraphQLArgument;
import graphqljpa.schema.GraphQLBuilder;

import javax.persistence.metamodel.EntityType;
import java.util.List;
import java.util.Set;

public interface GraphQLManagedTypeMetadata extends GraphQLMetaData {
    /**
     * @return boolean
     */
    boolean isRoot();

    /**
     * @return boolean
     */
    boolean isBasic();

    /**
     * @return boolean
     */
    boolean isEntity();

    /**
     * @return boolean
     */
    boolean isEmbeddable();

    /**
     * @return boolean
     */
    boolean isMappedSuperClass();

    /**
     * Obtain a Set of attributes metadata.
     * @return Set<GraphQLAttributeMetadata>
     */
    Set<GraphQLAttributeMetadata> getAttributes();

    List<GraphQLArgument> getArguments();

    /**
     * Get a GraphQLAttributeMetadata by name
     * @param name
     * @return
     */
    GraphQLAttributeMetadata getAttribute(String name);
    Class getJavaType();
}
