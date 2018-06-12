package graphqljpa.impl.metadata;

import graphqljpa.annotation.GraphQLFilterable;
import graphqljpa.impl.annotation.AnnotationUtils;
import graphqljpa.schema.metadata.GraphQLAttributeMetadata;
import graphqljpa.schema.metadata.GraphQLIdentifiableTypeMetadata;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.IdentifiableType;
import javax.persistence.metamodel.MappedSuperclassType;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

abstract class GraphQLIdentifiableTypeMetadataImpl extends GraphQLManagedTypeMetadataImpl implements GraphQLIdentifiableTypeMetadata {
    private final IdentifiableType identifiableType;
    private Set<GraphQLIdentifiableTypeMetadata> subclasses = new HashSet<>();
    private GraphQLIdentifiableTypeMetadata superClass;

    GraphQLIdentifiableTypeMetadataImpl(IdentifiableType identifiableType) {
        super(identifiableType);
        this.identifiableType = identifiableType;
    }

    @Override
    public boolean hasSubclasses() {
        return subclasses.size() > 0;
    }

    @Override
    public Set<GraphQLIdentifiableTypeMetadata> getSubclasses() {
        return new HashSet<>(subclasses);
    }

    @Override
    public void addSubclass(GraphQLIdentifiableTypeMetadata subclass) {
        this.subclasses.add(subclass);
    }

    @Override
    public GraphQLIdentifiableTypeMetadata getSupertype() {
        if (superClass != null) {
            return superClass;
        }

        IdentifiableType type = identifiableType.getSupertype();

        if (type instanceof EntityType) {
            GraphQLIdentifiableTypeMetadata entityMetadata = GraphQLMetadataFactory.getMetaData((EntityType) type);
            superClass = entityMetadata;
        } else if (type instanceof MappedSuperclassType) {
            GraphQLIdentifiableTypeMetadata mappedSuperclassMetadata = GraphQLMetadataFactory.getMetaData((MappedSuperclassType) type);
            superClass = mappedSuperclassMetadata;
        }

        return superClass;
    }

    @Override
    public List<GraphQLAttributeMetadata> getFilterableAttributes() {
        return getAttributes().stream().filter(AnnotationUtils::isFilterable).collect(Collectors.toList());
    }
}
