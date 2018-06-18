package graphqljpa.impl.metadata;

import graphql.schema.GraphQLList;
import graphql.schema.GraphQLOutputType;
import graphql.schema.GraphQLType;
import graphql.schema.GraphQLTypeReference;
import graphqljpa.impl.metadata.GraphQLMetadataFactory;
import graphqljpa.impl.metadata.GraphQLMetadataImpl;
import graphqljpa.scalars.JavaScalars;
import graphqljpa.schema.metadata.GraphQLAttributeMetadata;
import graphqljpa.schema.metadata.GraphQLManagedTypeMetadata;
import graphqljpa.schema.metadata.GraphQLMetaData;
import graphqljpa.impl.annotation.AnnotationUtils;

import javax.persistence.Entity;
import javax.persistence.metamodel.*;
import java.lang.annotation.Annotation;

import static graphql.Scalars.GraphQLString;

class GraphQLAttributeMetadataImpl extends GraphQLMetadataImpl implements GraphQLAttributeMetadata {
    private final Attribute attribute;
    private GraphQLManagedTypeMetadata entityMetadata = null;

    GraphQLAttributeMetadataImpl(Attribute attribute) {
        this.attribute = attribute;
    }

    @Override
    public GraphQLManagedTypeMetadata getDeclaringType() {
        ManagedType managedType = attribute.getDeclaringType();

        if (managedType instanceof EntityType) {
            return GraphQLMetadataFactory.getMetaData((EntityType) attribute.getDeclaringType());
        } else if (managedType instanceof EmbeddableType) {
            return GraphQLMetadataFactory.getMetaData((EmbeddableType) attribute.getDeclaringType());
        } else if (managedType instanceof MappedSuperclassType) {
            return GraphQLMetadataFactory.getMetaData((MappedSuperclassType) attribute.getDeclaringType());
        }

        throw new Error("Couldn't map attribute 'declaringType' to MetaData ");
    }

    @Override
    public GraphQLOutputType getType() {
        if (isBasic()) {
            return JavaScalars.of(attribute.getJavaType());
        }

        GraphQLOutputType type = null;

        if (isEmbedded()) {
            type = GraphQLTypeReference.typeRef(
                    GraphQLMetadataFactory.getEmbeddable(attribute.getJavaType().getSimpleName()).getName()
            );
        } else if (isAssociation()) {
            if (isCollection()) {
                type = GraphQLTypeReference.typeRef(
                        GraphQLMetadataFactory.getEntity(
                                ((PluralAttribute) attribute).getElementType().getJavaType().getSimpleName()
                        ).getName()
                );
            } else {
                type = GraphQLTypeReference.typeRef(
                        GraphQLMetadataFactory.getEntity(attribute.getJavaType().getSimpleName()).getName()
                );
            }
        } else if (isElementCollection()) {
            type = GraphQLString;
        }

        if (isCollection()) {
            type = GraphQLList.list(type);
        }

        if (type == null) {
            System.out.print("b");
        }

        return type;
    }

    @Override
    public Annotation getAnnotation(Class<? extends Annotation> clazz) {
        return attribute.getJavaType().getAnnotation(clazz);
    }

    @Override
    public String getName() {
        String name = AnnotationUtils.getName(this);
        if (name != null) {
            return name;
        }

        return attribute.getName();
    }

    @Override
    public String getDeprecationReason() {
        return AnnotationUtils.getDeprecated(this);
    }

    @Override
    public boolean isBasic() {
        return attribute.getPersistentAttributeType() == Attribute.PersistentAttributeType.BASIC;
    }

    @Override
    public boolean isManyToMany() {
        return attribute.getPersistentAttributeType() == Attribute.PersistentAttributeType.MANY_TO_MANY;
    }

    @Override
    public boolean isOneToOne() {
        return attribute.getPersistentAttributeType() == Attribute.PersistentAttributeType.ONE_TO_ONE;
    }

    @Override
    public boolean isManyToOne() {
        return attribute.getPersistentAttributeType() == Attribute.PersistentAttributeType.MANY_TO_ONE;
    }

    @Override
    public boolean isOneToMany() {
        return attribute.getPersistentAttributeType() == Attribute.PersistentAttributeType.ONE_TO_MANY;
    }

    @Override
    public boolean isElementCollection() {
        return attribute.getPersistentAttributeType() == Attribute.PersistentAttributeType.ELEMENT_COLLECTION;
    }

    @Override
    public boolean isEmbedded() {
        return attribute.getPersistentAttributeType() == Attribute.PersistentAttributeType.EMBEDDED;
    }

    /**
     * Represent a association and will do a join in the query.
     * Good place to support @defer. Instead of loading the whole data from SQL with joins we could load the data
     * without the joins and then do another request. If there isn't any @defer just load the whole data.
     * @return
     */
    @Override
    public boolean isAssociation() {
        return attribute.isAssociation();
    }

    @Override
    public boolean isCollection() {
        return attribute.isCollection();
    }
}
