package se.nackademin.domain.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import se.nackademin.domain.category.Category;
import se.nackademin.domain.event.Event;
import se.nackademin.domain.utilities.ApplicationUtilityFunctions;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import java.util.List;
import static junit.framework.TestCase.assertTrue;

@SuppressWarnings("Duplicates")
public class CalendarTest {
    private EntityManager manager;

    @Before
    public void setUp() throws Exception {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
        manager = factory.createEntityManager();
        ApplicationUtilityFunctions.createCalendar(manager);
        ApplicationUtilityFunctions.createCategories(manager);
        ApplicationUtilityFunctions.createEventsThenPersist(manager);
    }

    @Test
    public void selectAllCalendars() {
        List<Calendar> calendarList = ApplicationUtilityFunctions.getAllCalendars(manager);
        System.out.println(calendarList);
    }

    @Test
    public void selectAllEventsInCalendar() {
        Query query = manager.createQuery("SELECT event FROM Event event where event.calendar.name = 'Personal Calendar'", Event.class);
        List<Event> eventList = query.getResultList();
        eventList.forEach(System.out::println);
        for (Event event : eventList) {
            assertTrue(event.getCalendar().getName().equals("Personal Calendar"));
        }
    }

    @Test
    public void selectSpecificCalendar() {
        Calendar calendar = ApplicationUtilityFunctions.getCalendarMatchingName("Personal Calendar", manager);
        Category category = ApplicationUtilityFunctions.getCategoryMatchingName("Personal", manager);
        Event event = ApplicationUtilityFunctions.getEventMatchingName("Lunch", manager);
        // Assert ID on calendar and event.calendar are the same
        assertTrue(event.getCalendar().getId() == calendar.getId());
        // Assert hashCode are the same
        assertTrue(event.getCategory().hashCode() == category.hashCode());
        // Assert Objects are the same
        assertTrue(calendar.equals(event.getCalendar()));
    }

    @After
    public void tearDown() throws Exception {
        ApplicationUtilityFunctions.clearEventsFromDb(manager);
        ApplicationUtilityFunctions.clearCategoriesFromDb(manager);
        ApplicationUtilityFunctions.clearCalendarsFromDb(manager);
    }

}