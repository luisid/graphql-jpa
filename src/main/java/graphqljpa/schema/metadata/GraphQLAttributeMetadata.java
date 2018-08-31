package graphqljpa.schema.metadata;

import graphql.schema.GraphQLType;
import graphqljpa.schema.GraphQLExtension;

import javax.persistence.metamodel.Attribute;
import java.util.List;

public interface GraphQLAttributeMetadata extends GraphQLMetaData {
    /**
     * Get the parent class of the respective attribute. Instead of returning a ManagedType, it returns a
     * GraphQLManagedTypeMetadata
     * @return GraphQLManagedTypeMetadata
     */
    GraphQLManagedTypeMetadata getDeclaringType();

    /**
     * Return the original Attribute
     * @return Attribute
     */
    Attribute getAttribute();

    /**
     * Obtain the deprecation reason
     * @return String
     */
    String getDeprecationReason();

    /**
     * Still experimental
     * @return String
     */
    String getArgumentDescription();

    /**
     * Obtain the GraphQLType corresponding to this attribute.
     * @return GraphQLType
     */
    GraphQLType getType();

    /**
     * @return boolean
     */
    boolean isBasic();

    /**
     * @return boolean
     */
    boolean isManyToMany();

    /**
     * @return boolean
     */
    boolean isOneToOne();

    /**
     * @return boolean
     */
    boolean isManyToOne();

    /**
     * @return boolean
     */
    boolean isOneToMany();

    /**
     * @return boolean
     */
    boolean isElementCollection();

    /**
     * @return boolean
     */
    boolean isEmbedded();

    /**
     * @return boolean
     */
    boolean isAssociation();

    /**
     * @return boolean
     */
    boolean isCollection();
}
