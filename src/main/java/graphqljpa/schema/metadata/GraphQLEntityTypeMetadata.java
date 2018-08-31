package graphqljpa.schema.metadata;

import javax.persistence.metamodel.EntityType;

public interface GraphQLEntityTypeMetadata extends GraphQLIdentifiableTypeMetadata {
    /**
     * Obtain the original EntityType instance.
     * @return EntityType
     */
    EntityType<?> getEntityType();
}
