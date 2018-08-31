package graphqljpa.impl.metadata;

import graphql.Scalars;
import graphql.schema.GraphQLSchema;
import graphqljpa.annotation.GraphQLDescription;
import graphqljpa.impl.GraphQLSchemaBuilderImpl;
import graphqljpa.schema.GraphQLSchemaBuilder;
import graphqljpa.schema.metadata.GraphQLAttributeMetadata;
import graphqljpa.schema.metadata.GraphQLEntityTypeMetadata;
import org.hibernate.type.EntityType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.management.Attribute;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

public class GraphQLAttributeMetadataImplTest {
    private EntityManagerFactory entityManagerFactory;
    private GraphQLAttributeMetadata metadata;
    private GraphQLAttributeMetadata attributeMetadata;
    private GraphQLEntityTypeMetadata entityTypeMetadata;

    @Before
    public void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory( "star-wars" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityTypeMetadata = GraphQLMetadataFactory.getMetaData(
                entityManager.getMetamodel().getEntities().stream()
                        .filter(entityType -> entityType.getName().equals("Human")).findFirst().get()
        );
        attributeMetadata = entityTypeMetadata.getAttribute("appearsIn");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getDeclaringType() throws Exception {
        assertEquals(attributeMetadata.getDeclaringType().getName(), "Character");
    }

//    @Test
//    public void getAttribute() throws Exception {
//
//    }
//
//    @Test
//    public void getType() throws Exception {
//
//    }

    @Test
    public void getAnnotation() throws Exception {
        GraphQLDescription annotation = (GraphQLDescription) attributeMetadata.getAnnotation(GraphQLDescription.class);
        assertEquals(annotation.value(), "What Star Wars episodes does this character appear in");
    }
    @Test
    public void getOriginalName() throws Exception {
        assertEquals(attributeMetadata.getName(), "appearsIn");
    }

    @Test
    public void getName() throws Exception {
        assertEquals(attributeMetadata.getOriginalName(), "appearsIn");
    }

    @Test
    public void getDeprecationReason() throws Exception {
        assertEquals(attributeMetadata.getDeprecationReason(), null);
    }

    @Test
    public void getArgumentDescription() throws Exception {
    }

    @Test
    public void isBasic() throws Exception {
        assertEquals(attributeMetadata.isBasic(), false);
    }

    @Test
    public void isManyToMany() throws Exception {
        assertEquals(attributeMetadata.isManyToMany(), false);
    }

    @Test
    public void isOneToOne() throws Exception {
        assertEquals(attributeMetadata.isOneToOne(), false);
    }

    @Test
    public void isManyToOne() throws Exception {
        assertEquals(attributeMetadata.isManyToOne(), false);
    }

    @Test
    public void isOneToMany() throws Exception {
        assertEquals(attributeMetadata.isOneToMany(), false);
    }

    @Test
    public void isElementCollection() throws Exception {
        assertEquals(attributeMetadata.isElementCollection(), true);
    }

    @Test
    public void isEmbedded() throws Exception {
        assertEquals(attributeMetadata.isEmbedded(), false);
    }

    @Test
    public void isAssociation() throws Exception {
        assertEquals(attributeMetadata.isAssociation(), false);
    }

    @Test
    public void isCollection() throws Exception {
        assertEquals(attributeMetadata.isCollection(), true);
    }

}