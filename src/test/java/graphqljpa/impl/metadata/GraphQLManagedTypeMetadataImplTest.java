package graphqljpa.impl.metadata;

import graphqljpa.schema.metadata.GraphQLAttributeMetadata;
import graphqljpa.schema.metadata.GraphQLEntityTypeMetadata;
import graphqljpa.schema.metadata.GraphQLManagedTypeMetadata;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.management.Attribute;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

public class GraphQLManagedTypeMetadataImplTest {
    private EntityManagerFactory entityManagerFactory;
    private GraphQLManagedTypeMetadata managedTypeMetadata;

    @Before
    public void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory( "star-wars" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        managedTypeMetadata = GraphQLMetadataFactory.getMetaData(
                entityManager.getMetamodel().getEntities().stream()
                        .filter(entityType -> entityType.getName().equals("Human")).findFirst().get()
        );
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getName() throws Exception {
        assertEquals(managedTypeMetadata.getName(), "Human");
    }

    @Test
    public void isRoot() throws Exception {
        assertEquals(managedTypeMetadata.isRoot(), true);
    }

    @Test
    public void isBasic() throws Exception {
        assertEquals(managedTypeMetadata.isBasic(), false);
    }

    @Test
    public void isEntity() throws Exception {
        assertEquals(managedTypeMetadata.isEntity(), true);
    }

    @Test
    public void isEmbeddable() throws Exception {
        assertEquals(managedTypeMetadata.isEmbeddable(), false);
    }

    @Test
    public void isMappedSuperClass() throws Exception {
        assertEquals(managedTypeMetadata.isMappedSuperClass(), false);
    }

}