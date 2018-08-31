package graphqljpa.schema.metadata;

import javax.persistence.metamodel.IdentifiableType;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;
import java.util.List;
import java.util.Set;

public interface GraphQLIdentifiableTypeMetadata extends GraphQLManagedTypeMetadata {
    // getId should be useful but don't know how yet

    boolean hasSubclasses();
    Set<GraphQLIdentifiableTypeMetadata> getSubclasses();
    GraphQLIdentifiableTypeMetadata getSupertype();
    void addSubclass(GraphQLIdentifiableTypeMetadata subclass);
    List<GraphQLAttributeMetadata> getFilterableAttributes();

    /**
     * Returns the corresponding attibute metadata for the id property.
     * @return GraphQLAttributeMetadata
     */
    GraphQLAttributeMetadata getId();
}
