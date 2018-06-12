package graphqljpa.impl.metadata;

import graphqljpa.schema.metadata.GraphQLEmbeddableTypeMetadata;

import javax.persistence.metamodel.EmbeddableType;

class GraphQLEmbeddableTypeMetadataImpl extends GraphQLManagedTypeMetadataImpl implements GraphQLEmbeddableTypeMetadata {
    GraphQLEmbeddableTypeMetadataImpl(EmbeddableType embeddableType) {
        super(embeddableType);
    }
}
