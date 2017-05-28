package se.nackademin.domain.utilities;

import se.nackademin.domain.category.Category;
import se.nackademin.domain.event.Event;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class ApplicationUtilityFunctions {
    // Create entities
    public static void createCategories(EntityManager manager) {
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

    public static void createEventsThenPersist(EntityManager manager) {
        try {
            manager.getTransaction().begin();
            if (manager.createQuery("select e from Event e", Event.class).getResultList().size() == 0) {
                manager.persist(
                        new Event("Dentist", "dentist appointment", "2017-06-01",
                                "2017-06-01", "10:00", "10:00",
                                getCategoryMatchingName("Personal", manager)));
                manager.persist(
                        new Event("Lunch", "lunch meeting with friend", "2017-06-02",
                                "2017-06-02", "12:00", "13:00",
                                getCategoryMatchingName("Personal", manager)));
                manager.persist(
                        new Event("Office party", "office party at clarion hotel", "2017-07-12",
                                "2017-07-13", "18:00", "03:00",
                                getCategoryMatchingName("Work", manager)));
                manager.persist(
                        new Event("Java Backend", "backend course with Bjorn", "2017-05-31",
                                "2017-05-31", "09:00", "16:00",
                                getCategoryMatchingName("School", manager)));
            }
            manager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Clear db entities

    public static void clearCategoriesFromDb(EntityManager manager) {
        EntityTransaction entityTransaction = manager.getTransaction();
        List<Category> resultList = getAllCategories(manager);
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
    public static void clearEventsFromDb(EntityManager manager) {
        EntityTransaction entityTransaction = manager.getTransaction();
        List<Event> resultList = getAllEvents(manager);
        entityTransaction.begin();
        try {
            for (Event event : resultList) {
                manager.remove(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        entityTransaction.commit();
    }

    // Get entities
    public static List<Category> getAllCategories(EntityManager manager) {
        return manager.createQuery("select a from Category a", Category.class).getResultList();
    }

    public static List<Event> getAllEvents(EntityManager manager) {
        return manager.createQuery("select e from Event e", Event.class).getResultList();
    }

    public static Category getCategoryMatchingName(String categoryName, EntityManager manager) {
        Query query = manager.createNativeQuery(
                "SELECT * from Category a where a.name = ?", Category.class);
        query.setParameter(1, categoryName);
        return (Category) query.getResultList().get(0);
    }

}
