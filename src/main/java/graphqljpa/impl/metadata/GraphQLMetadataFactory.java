package graphqljpa.impl.metadata;

import graphql.schema.GraphQLObjectType;
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
        GraphQLEntityTypeMetadataImpl entityMetadata = new GraphQLEntityTypeMetadataImpl(entityType);
        String name = entityMetadata.getName();

        if (entityMetadataMap.containsKey(name)) {
            return entityMetadataMap.get(name);
        }

        entityMetadataMap.putIfAbsent(name, entityMetadata);

        return entityMetadata;
    }

    static public GraphQLMappedSuperclassTypeMetadata getMetaData(MappedSuperclassType mappedSuperclassType) {
        GraphQLMappedSuperclassTypeMetadataImpl mappedSuperclassMetadata = new GraphQLMappedSuperclassTypeMetadataImpl(mappedSuperclassType);
        String name = mappedSuperclassMetadata.getName();

        if (mappedSuperclassMetadataMap.containsKey(name)) {
            return mappedSuperclassMetadataMap.get(name);
        }

        mappedSuperclassMetadataMap.putIfAbsent(name, mappedSuperclassMetadata);

        return mappedSuperclassMetadata;
    }

    static public GraphQLEmbeddableTypeMetadata getMetaData(EmbeddableType embeddableType) {
        GraphQLEmbeddableTypeMetadataImpl embeddableTypeMetadata = new GraphQLEmbeddableTypeMetadataImpl(embeddableType);
        String name = embeddableTypeMetadata.getName();

        if (embeddableMetadataMap.containsKey(name)) {
            return embeddableMetadataMap.get(name);
        }

        embeddableMetadataMap.putIfAbsent(name, embeddableTypeMetadata);

        return embeddableTypeMetadata;
    }

    static public GraphQLAttributeMetadata getMetaData(Attribute attribute) {
        return new GraphQLAttributeMetadataImpl(attribute);
    }


    static public GraphQLManagedTypeMetadata getMetadataFromObjectType(GraphQLObjectType objectType) {
        if (entityMetadataMap.containsKey(objectType.getName())) {
            return entityMetadataMap.get(objectType.getName());
        }

        if (embeddableMetadataMap.containsKey(objectType.getName())) {
            return embeddableMetadataMap.get(objectType.getName());
        }

        if (mappedSuperclassMetadataMap.containsKey(objectType.getName())) {
            return mappedSuperclassMetadataMap.get(objectType.getName());
        }

        return null;
    }
}
