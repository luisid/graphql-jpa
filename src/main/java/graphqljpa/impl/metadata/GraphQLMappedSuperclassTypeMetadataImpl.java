package graphqljpa.impl.metadata;

import graphqljpa.schema.metadata.GraphQLMappedSuperclassTypeMetadata;

import javax.persistence.metamodel.MappedSuperclassType;

class GraphQLMappedSuperclassTypeMetadataImpl extends GraphQLIdentifiableTypeMetadataImpl implements GraphQLMappedSuperclassTypeMetadata {
    GraphQLMappedSuperclassTypeMetadataImpl(MappedSuperclassType mappedSuperclassType) {
        super(mappedSuperclassType);
    }
}
