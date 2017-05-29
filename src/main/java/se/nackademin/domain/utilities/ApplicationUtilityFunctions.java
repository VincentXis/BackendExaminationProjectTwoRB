package se.nackademin.domain.utilities;

import se.nackademin.domain.Calendar.Calendar;
import se.nackademin.domain.category.Category;
import se.nackademin.domain.event.Event;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class ApplicationUtilityFunctions {
    // Create new entities for testing
    public static void persistNewCalendar(Calendar calendar, EntityManager manager) {
        try {
            manager.getTransaction().begin();
            Query query = manager.createNativeQuery("SELECT * from Calendar a where a.name = ?", Calendar.class);
            query.setParameter(1, calendar.getName());
            List<Calendar> resultList = query.getResultList();
            if (resultList.isEmpty()) {
                manager.persist(calendar);
            }
            manager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void persistNewCategory(Category category, EntityManager manager) {
        try {
            manager.getTransaction().begin();
            Query query = manager.createNativeQuery("SELECT * from Category a where a.name = ?", Category.class);
            query.setParameter(1, category.getName());
            List<Calendar> resultList = query.getResultList();
            if (resultList.isEmpty()) {
                manager.persist(category);
            }
            manager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Create entities for setup
    public static void createCalendar(EntityManager manager) {
        try {
            manager.getTransaction().begin();
            if (manager.createQuery("select a from Calendar a", Calendar.class).getResultList().size() == 0) {
                manager.persist(new Calendar("Personal Calendar"));
            }
            manager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
                        new Event("Dentist", "dentist appointment", "2017-06-01", "2017-06-01",
                                getCategoryMatchingName("Personal", manager),
                                getCalendarMatchingName("Personal Calendar", manager)));
                manager.persist(
                        new Event("Lunch", "lunch meeting with friend", "2017-06-02", "2017-06-02",
                                getCategoryMatchingName("Personal", manager),
                                getCalendarMatchingName("Personal Calendar", manager)));
                manager.persist(
                        new Event("Office party", "office party at clarion hotel", "2017-07-12", "2017-07-13",
                                getCategoryMatchingName("Work", manager),
                                getCalendarMatchingName("Personal Calendar", manager)));
                manager.persist(
                        new Event("Java Backend", "backend course with Bjorn", "2017-05-31", "2017-05-31",
                                getCategoryMatchingName("School", manager),
                                getCalendarMatchingName("Personal Calendar", manager)));
            }
            manager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Clear db entities
    public static void clearCalendarsFromDb(EntityManager manager) {
        EntityTransaction entityTransaction = manager.getTransaction();
        List<Calendar> resultList = getAllCalendars(manager);
        entityTransaction.begin();
        try {
            for (Calendar calendar : resultList) {
                manager.remove(calendar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        entityTransaction.commit();
    }

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
    public static List<Calendar> getAllCalendars(EntityManager manager) {
        return manager.createQuery("select a from Calendar a", Calendar.class).getResultList();
    }

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

    public static Calendar getCalendarMatchingName(String calendarName, EntityManager manager) {
        Query query = manager.createNativeQuery(
                "SELECT * from Calendar a where a.name = ?", Calendar.class);
        query.setParameter(1, calendarName);
        return (Calendar) query.getResultList().get(0);
    }

    public static Event getEventMatchingName(String eventName, EntityManager manager) {
        Query query = manager.createNativeQuery(
                "SELECT * from Event a where a.name = ?", Event.class);
        query.setParameter(1, eventName);
        return (Event) query.getResultList().get(0);
    }

}
