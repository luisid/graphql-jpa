package graphqljpa.impl.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLObjectType;
import graphqljpa.impl.metadata.GraphQLMetadataFactory;
import graphqljpa.schema.metadata.GraphQLAttributeMetadata;
import graphqljpa.schema.metadata.GraphQLEntityTypeMetadata;
import graphqljpa.schema.metadata.GraphQLManagedTypeMetadata;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.PluralAttribute;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public class OneToManyDataFetcher implements DataFetcher {
    private EntityManager entityManager;
    private GraphQLAttributeMetadata metadata;
    private PluralAttribute attribute;
    private String idArgument = "id";

    public OneToManyDataFetcher(EntityManager entityManager, GraphQLAttributeMetadata metadata, PluralAttribute attribute) {
        this.entityManager = entityManager;
        this.attribute = attribute;
        this.metadata = metadata;
    }


    @Override
    public Object get(DataFetchingEnvironment environment) {
        // Get parent entity

        Object object = environment.getSource();

        return getAttributeValue(object, attribute);
    }

    /**
     * Fetches the value of the given PluralAttribute on the given
     * entity.
     *
     * @see http://stackoverflow.com/questions/7077464/how-to-get-singularattribute-mapped-value-of-a-persistent-object
     * @see https://github.com/introproventures/graphql-jpa-query/blob/master/graphql-jpa-query-schema/src/main/java/com/introproventures/graphql/jpa/query/schema/impl/GraphQLJpaOneToManyDataFetcher.java
     */
    @SuppressWarnings("unchecked")
    public <EntityType, FieldType> FieldType getAttributeValue(EntityType entity, PluralAttribute<EntityType, ?, FieldType> field) {
        try {
            Member member = field.getJavaMember();
            if (member instanceof Method) {
                // this should be a getter method:
                return (FieldType) ((Method)member).invoke(entity);
            } else if (member instanceof java.lang.reflect.Field) {
                return (FieldType) ((java.lang.reflect.Field)member).get(entity);
            } else {
                throw new IllegalArgumentException("Unexpected java member type. Expecting method or field, found: " + member);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
