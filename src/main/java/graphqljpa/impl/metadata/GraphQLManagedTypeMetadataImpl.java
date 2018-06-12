package graphqljpa.impl.metadata;

import graphql.schema.DataFetcher;
import graphql.schema.GraphQLArgument;
import graphqljpa.annotation.GraphQLFilterable;
import graphqljpa.schema.GraphQLBuilder;
import graphqljpa.schema.metadata.GraphQLManagedTypeMetadata;
import graphqljpa.schema.metadata.GraphQLAttributeMetadata;
import graphqljpa.schema.metadata.GraphQLMetaData;
import graphqljpa.impl.annotation.AnnotationUtils;

import javax.persistence.metamodel.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

abstract class GraphQLManagedTypeMetadataImpl extends GraphQLMetadataImpl implements GraphQLManagedTypeMetadata {
    private final ManagedType managedType;

    GraphQLManagedTypeMetadataImpl(ManagedType managedType) {
        this.managedType = managedType;
    }

    @Override
    public GraphQLAttributeMetadata getAttribute(String name) {
        return null;
    }

    @Override
    public Set<GraphQLAttributeMetadata> getAttributes() {
        Set<Attribute> attributes = managedType.getAttributes();
        Set<GraphQLAttributeMetadata> metaDatas = new HashSet<>();

        for (Iterator<Attribute> iterator = attributes.iterator(); iterator.hasNext();) {
            metaDatas.add(GraphQLMetadataFactory.getMetaData(iterator.next()));
        }
        return metaDatas;
    }

    @Override
    public Annotation getAnnotation(Class<? extends Annotation> clazz) {
        return managedType.getJavaType().getAnnotation(clazz);
    }

    @Override
    public String getName() {
        String name = AnnotationUtils.getName(this);

        if (name != null) {
            return name;
        }

        return managedType.getJavaType().getName();
    }

    @Override
    public boolean isRoot() {
        return AnnotationUtils.isRoot(this);
    }

    @Override
    public boolean isBasic() {
        return managedType.getPersistenceType() == Type.PersistenceType.BASIC;
    }

    @Override
    public boolean isEntity() {
        return managedType.getPersistenceType() == Type.PersistenceType.ENTITY;
    }

    @Override
    public boolean isEmbeddable() {
        return managedType.getPersistenceType() == Type.PersistenceType.EMBEDDABLE;
    }

    @Override
    public boolean isMappedSuperClass() {
        return managedType.getPersistenceType() == Type.PersistenceType.MAPPED_SUPERCLASS;
    }

    @Override
    public List<GraphQLArgument> getArguments() {
        return new ArrayList<>();
    }

    @Override
    public Class getJavaType() {
        return managedType.getJavaType();
    }
}