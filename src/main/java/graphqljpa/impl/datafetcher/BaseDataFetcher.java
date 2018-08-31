package graphqljpa.impl.datafetcher;

import graphql.language.Field;
import graphql.language.Selection;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphqljpa.schema.metadata.GraphQLAttributeMetadata;
import graphqljpa.schema.metadata.GraphQLEntityTypeMetadata;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import java.util.List;
import java.util.stream.Collectors;

public class BaseDataFetcher implements DataFetcher<Object> {
    private static final String TYPENAME = "__typename";

    protected final EntityManager entityManager;
    protected final GraphQLEntityTypeMetadata metadata;

    /**
     * Creates JPA entity DataFetcher instance
     *
     * @param entityManager
     * @param metadata
     */
    public BaseDataFetcher(EntityManager entityManager, GraphQLEntityTypeMetadata metadata) {
        this.entityManager = entityManager;
        this.metadata = metadata;
    }

    @Override
    public Object get(DataFetchingEnvironment environment) {
        List<Object> result = getQuery(environment).getResultList();
        return result;
    }

    private TypedQuery getQuery(DataFetchingEnvironment environment) {
        CriteriaQuery<?> query = createCriteriaQuery(environment);
        Root<?> from = query.from(metadata.getEntityType());
        Field field = environment.getField();

//        List<javax.persistence.criteria.Selection<?>> selections = field.getSelectionSet().getSelections()
//                .stream()
//                .filter(selection -> this.clean(selection, from))
//                .map(selection -> this.getCriteriaSelection(selection, from))
//                .collect(Collectors.toList());
//
//        query.multiselect(selections);

        from.alias(from.getModel().getName());

        return entityManager.createQuery(query.distinct(true));
    }

    private Boolean clean(Selection selection, Root<?> from) {
        Field selectedField = (Field) selection;
        Path<?> fieldPath = from.get(selectedField.getName());

        return TYPENAME.equals(selectedField.getName()) || !(fieldPath.getModel() instanceof Attribute);
    }

    public Boolean removeNull(Object o) {
        return o == null;
    }


    private javax.persistence.criteria.Selection<?> getCriteriaSelection(Selection selection, Root<?> from) {
        // Issue in can graphql field name is not the same as entity field name
        Field selectedField = (Field) selection;
        GraphQLAttributeMetadata attributeMetadata = metadata.getAttribute(selectedField.getName());

        return from.get(attributeMetadata.getName());
    }

    private CriteriaQuery<?> createCriteriaQuery(DataFetchingEnvironment environment) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        return builder.createQuery(metadata.getJavaType());
    }
}
