package se.nackademin.domain.category;

import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import java.util.List;

public class CategoryTest {
    private EntityManager manager;

    @Before
    public void setUp() throws Exception {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
        manager = factory.createEntityManager();
    }

    @Test
    public void createNewCategory() {
        createCategories();
        Category category = new Category("Other");
        manager.persist(category);
        List<Category> categories = getAllCategories();
        System.out.println(categories);
        clearCategoriesFromDb();
    }

    @Test
    public void selectSpecificCategory() {
        createCategories();
        Query query = manager.createQuery("select a from Category a where a.name = 'Personal'", Category.class);

        System.out.println(query.getResultList().get(0));
        Category cat = getCategoryMatchingName("Personal");
        System.out.println(cat);
        clearCategoriesFromDb();
    }


    // Helper functions
    public void createCategories() {
        try {
            manager.getTransaction().begin();
            if (manager.createQuery("select a from Category a", Category.class).getResultList().size() == 0) {
                manager.persist(new Category("Personal"));
                manager.persist(new Category("Work"));
                manager.persist(new Category("School"));
            }
            manager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Category> getAllCategories() {
        return manager.createQuery("select a from Category a", Category.class).getResultList();
    }

    public Category getCategoryMatchingName(String categoryName) {
        setUpManager();
        createCategories();
        Query query = manager.createNativeQuery(
                "SELECT * from Category a where a.name = ?", Category.class);
        query.setParameter(1, categoryName);
        return (Category) query.getResultList().get(0);
    }

    // Clear Categories from db
    public void clearCategoriesFromDb() {
        EntityTransaction entityTransaction = manager.getTransaction();
        List<Category> resultList = getAllCategories();
        entityTransaction.begin();
        try {
            for (Category category : resultList) {
                manager.remove(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        entityTransaction.commit();
    }
    private void setUpManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
        manager = factory.createEntityManager();
    }
}