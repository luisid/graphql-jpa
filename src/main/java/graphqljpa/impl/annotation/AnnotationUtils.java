package graphqljpa.impl.annotation;

import graphqljpa.annotation.*;
import graphqljpa.schema.metadata.GraphQLAttributeMetadata;
import graphqljpa.schema.metadata.GraphQLMetaData;

public class AnnotationUtils {
    static public String getName(GraphQLMetaData metaData) {
        final GraphQLName annotation = (GraphQLName) metaData.getAnnotation(GraphQLName.class);

        return annotation != null? annotation.value(): null;
    }

    static public String getDescription(GraphQLMetaData metaData) {
        final GraphQLDescription annotation = (GraphQLDescription) metaData.getAnnotation(GraphQLDescription.class);

        return annotation != null? annotation.value(): null;
    }

    static public String getDeprecated(GraphQLMetaData metaData) {
        final GraphQLDeprecated annotation = (GraphQLDeprecated) metaData.getAnnotation(GraphQLDeprecated.class);

        return annotation != null ? annotation.value() : null;
    }

    static public Boolean isIgnored(GraphQLMetaData metaData) {
        final GraphQLIgnore annotation = (GraphQLIgnore) metaData.getAnnotation(GraphQLIgnore.class);
        return annotation.value();
    }

    static public Boolean isRoot(GraphQLMetaData metaData) {
        final GraphQLIsRoot annotation = (GraphQLIsRoot) metaData.getAnnotation(GraphQLIsRoot.class);

        return annotation.value();
    }

    static public Class<? extends graphqljpa.schema.GraphQLBuilder> getBuilder(GraphQLMetaData metaData) {
        final GraphQLBuilder annotation = (GraphQLBuilder) metaData.getAnnotation(GraphQLBuilder.class);

        return annotation.builder();
    }

    static public Class<? extends graphqljpa.schema.GraphQLBuilder>[] getBuilders(GraphQLMetaData metaData) {
        final GraphQLBuilder annotation = (GraphQLBuilder) metaData.getAnnotation(GraphQLBuilder.class);

        return annotation.builders();
    }

    static public Boolean isExtensible(GraphQLMetaData metaData) {
        final GraphQLExtensible annotation = (GraphQLExtensible) metaData.getAnnotation(GraphQLExtensible.class);

        return annotation.value();
    }

    static public boolean isFilterable(GraphQLAttributeMetadata metadata) {
        GraphQLFilterable filterable = (GraphQLFilterable) metadata.getAnnotation(GraphQLFilterable.class);

        return filterable != null && filterable.value();
    }
}
