package se.nackademin;

import se.nackademin.domain.category.Category;
import se.nackademin.domain.event.Event;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class Calendar {
    private EntityManager manager;

    public Calendar(EntityManager manager) {
        this.manager = manager;
    }

    // Create new entities for testing
    public void persistNewCategory(Category category) {
        try {
            manager.getTransaction().begin();
            Query query = manager.createNativeQuery("SELECT * from Category a where a.name = ?", Category.class);
            query.setParameter(1, category.getName());
            List<Category> resultList = query.getResultList();
            if (resultList.isEmpty()) {
                manager.persist(category);
            }
            manager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void persistNewEventToCategory(String categoryName, String eventName, String eventDescription, String eventStartDate, String eventEndDate) {
        Category category = getCategoryMatchingName(categoryName);
        category.getEventList().add(new Event(eventName, eventDescription, eventStartDate, eventEndDate, category));
        manager.getTransaction().begin();
        manager.persist(category);
        manager.getTransaction().commit();
    }

    // Create entities for setup
    public void fillDatabase() {
        Category personal = new Category("Personal");
        Category work = new Category("Work");
        Category school = new Category("School");

        manager.getTransaction().begin();
        manager.persist(personal);
        manager.persist(school);
        manager.persist(work);
        manager.getTransaction().commit();

        personal.getEventList().add(new Event("Dentist", "dentist appointment", "2017-06-01", "2017-06-01", personal));
        personal.getEventList().add(new Event("Lunch", "lunch meeting with friend", "2017-06-02", "2017-06-02", personal));
        work.getEventList().add(new Event("Office party", "office party at clarion hotel", "2017-07-12", "2017-07-13", work));
        school.getEventList().add(new Event("Java Backend", "backend course with Bjorn", "2017-05-31", "2017-05-31", school));

        manager.getTransaction().begin();
        manager.persist(personal);
        manager.persist(school);
        manager.persist(work);
        manager.getTransaction().commit();

    }

    // Clear db entities
    public void clearDb() {
        EntityTransaction et = manager.getTransaction();
        List<Event> eventResultList = getAllEvents();
        List<Category> categoryResultList = getAllCategories();
        et.begin();
        try {
            for (Event event : eventResultList) {
                manager.remove(event);
            }
            for (Category category : categoryResultList) {
                manager.remove(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        et.commit();
    }

    // Get entities
    public List<Category> getAllCategories() {
        return manager.createQuery("select a from Category a order by a.id", Category.class).getResultList();
    }

    public List<Event> getAllEvents() {
        return manager.createQuery("select e from Event e order by e.id", Event.class).getResultList();
    }

    public List<Event> getEventsByStartDate(Date date) {
        Query eventByStartDateQuery =
                manager.createQuery("SELECT a from Event a where a.startDate = :date order by a.id", Event.class)
                        .setParameter("date", date);
        return eventByStartDateQuery.getResultList();
    }

    public List<Event> getEventsByStartDateAndCategory(Date date, String category) {
        Query eventByStartDateAndCategoryQuery = manager
                .createQuery("SELECT a from Event a where a.startDate = :date and a.category.name = :category order by a.id", Event.class)
                .setParameter("date", date)
                .setParameter("category", category);
        return eventByStartDateAndCategoryQuery.getResultList();
    }

    public List<Event> getEventsByCategory(String category) {
        Query eventByStartDateAndCategoryQuery = manager
                .createQuery("SELECT a from Event a where a.category.name = :category order by a.id", Event.class)
                .setParameter("category", category);
        return eventByStartDateAndCategoryQuery.getResultList();
    }

    public Category getCategoryMatchingName(String categoryName) {
        Query query = manager.createNativeQuery("SELECT * from Category a where a.name = ?", Category.class);
        query.setParameter(1, categoryName);
        return (Category) query.getResultList().get(0);
    }

}
