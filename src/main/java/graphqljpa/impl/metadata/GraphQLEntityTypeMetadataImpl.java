package graphqljpa.impl.metadata;

import graphqljpa.impl.annotation.AnnotationUtils;
import graphqljpa.schema.metadata.GraphQLEntityTypeMetadata;

import javax.persistence.metamodel.EntityType;

class GraphQLEntityTypeMetadataImpl extends GraphQLIdentifiableTypeMetadataImpl implements GraphQLEntityTypeMetadata {
    final EntityType entityType;

    GraphQLEntityTypeMetadataImpl(EntityType entityType) {
        super(entityType);
        this.entityType = entityType;
    }

    @Override
    public String getName() {
        String name = AnnotationUtils.getName(this);

        if (name != null) {
            return name;
        }

        return entityType.getName();
    }
}
