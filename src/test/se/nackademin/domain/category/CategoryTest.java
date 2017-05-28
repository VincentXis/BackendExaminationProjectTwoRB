package se.nackademin.domain.category;

import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class CategoryTest {
    private EntityManager manager;

    @Before
    public void setUp() throws Exception {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
        manager = factory.createEntityManager();
        manager.getTransaction().begin();
    }

    @Test
    public void createNewCategory() {
        createCategories();
        Category category = new Category("Other");
        manager.persist(category);
        List<Category> categories = getCategoryList();
        System.out.println(categories);
    }

    @Test
    public void selectSpecificCategory() {
        createCategories();
        Query query = manager.createQuery("select a from Category a where a.name = 'Personal'", Category.class);

        System.out.println(query.getResultList().get(0));
    }


    // Helper functions
    public void createCategories() {
        int numberOfCategories = manager.createQuery("select a from Category a", Category.class).getResultList().size();
        if (numberOfCategories == 0) {
            manager.persist(new Category("Personal"));
            manager.persist(new Category("Work"));
            manager.persist(new Category("School"));
        }
    }

    public List<Category> getCategoryList() {
        return manager.createQuery("select a from Category a", Category.class).getResultList();
    }

}