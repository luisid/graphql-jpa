package graphqljpa.impl.metadata;

import graphqljpa.schema.metadata.*;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.MappedSuperclassType;
import java.util.HashMap;
import java.util.Map;

public class GraphQLMetadataFactory {
    static private Map<String, GraphQLEntityTypeMetadata> entityMetadataMap = new HashMap<>();
    static private Map<String, GraphQLMappedSuperclassTypeMetadata> mappedSuperclassMetadataMap = new HashMap<>();
    static private Map<String, GraphQLEmbeddableTypeMetadata> embeddableMetadataMap = new HashMap<>();

    static public GraphQLEntityTypeMetadata getEntity(String name) {
        if (entityMetadataMap.containsKey(name)) {
            return entityMetadataMap.get(name);
        }

        return null;
    }

    static public Map<String, GraphQLEntityTypeMetadata> getEntities() {
        return new HashMap<>(entityMetadataMap);
    }

    static public GraphQLEmbeddableTypeMetadata getEmbeddable(String name) {
        if (embeddableMetadataMap.containsKey(name)) {
            return embeddableMetadataMap.get(name);
        }

        return null;
    }

    static public Map<String, GraphQLEmbeddableTypeMetadata> getEmbeddables() {
        return new HashMap<>(embeddableMetadataMap);
    }

    static public GraphQLMappedSuperclassTypeMetadata getMappedSuperclass(String name) {
        if (mappedSuperclassMetadataMap.containsKey(name)) {
            return mappedSuperclassMetadataMap.get(name);
        }

        return null;
    }


    static public GraphQLEntityTypeMetadata getMetaData(EntityType entityType) {
        if (entityMetadataMap.containsKey(entityType.getName())) {
            return entityMetadataMap.get(entityType.getName());
        }

        GraphQLEntityTypeMetadataImpl entityMetadata = new GraphQLEntityTypeMetadataImpl(entityType);

        entityMetadataMap.putIfAbsent(entityType.getName(), entityMetadata);

        return entityMetadata;
    }

    static public GraphQLMappedSuperclassTypeMetadata getMetaData(MappedSuperclassType mappedSuperclassType) {
        String name = mappedSuperclassType.getJavaType().getName();

        if (mappedSuperclassMetadataMap.containsKey(name)) {
            return mappedSuperclassMetadataMap.get(name);
        }

        GraphQLMappedSuperclassTypeMetadataImpl mappedSuperclassMetadata = new GraphQLMappedSuperclassTypeMetadataImpl(mappedSuperclassType);

        return mappedSuperclassMetadataMap.putIfAbsent(name, mappedSuperclassMetadata);
    }

    static public GraphQLEmbeddableTypeMetadata getMetaData(EmbeddableType embeddableType) {
        String name = embeddableType.getJavaType().getName();

        if (embeddableMetadataMap.containsKey(name)) {
            return embeddableMetadataMap.get(name);
        }

        GraphQLEmbeddableTypeMetadataImpl embeddableTypeMetadata = new GraphQLEmbeddableTypeMetadataImpl(embeddableType);

        return embeddableMetadataMap.putIfAbsent(name, embeddableTypeMetadata);
    }

    static public GraphQLAttributeMetadata getMetaData(Attribute attribute) {
        return new GraphQLAttributeMetadataImpl(attribute);
    }
}
