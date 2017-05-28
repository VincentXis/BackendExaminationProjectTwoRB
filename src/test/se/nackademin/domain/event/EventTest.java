package se.nackademin.domain.event;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import se.nackademin.domain.utilities.ApplicationUtilityFunctions;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

@SuppressWarnings("Duplicates")
public class EventTest {
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
    public void createAnEventWithoutCalendarAndCategory() {
        Event event = new Event("Dentist", "dentist appointment", "2017-06-01", "2017-06-01", "10:00", "10:00");
        System.out.println(event);
        assertTrue(event.getName().equals("Dentist"));
    }

    @Test
    public void getAllEvents() {
        List<Event> eventList = ApplicationUtilityFunctions.getAllEvents(manager);
        assertTrue(!eventList.isEmpty());
        eventList.forEach(System.out::println);
    }

    @Test
    public void getAllEventsMatchingCategory() {
        String divider = "----------------------------------------";
        Query categoryPersonal = manager.createQuery("select e from Event e where e.category.name = 'Personal'", Event.class);
        List<Event> personalEvents = categoryPersonal.getResultList();
        Query categoryWork = manager.createQuery("select e from Event e where e.category.name = 'Work'", Event.class);
        List<Event> workEvents = categoryWork.getResultList();

        System.out.println("Get all events with the Personal category\n" + divider);
        personalEvents.forEach(System.out::println);
        System.out.println(divider);
        System.out.println("Get all events with the Work category\n" + divider);
        workEvents.forEach(System.out::println);
    }



    @After
    public void tearDown() throws Exception {
        ApplicationUtilityFunctions.clearEventsFromDb(manager);
        ApplicationUtilityFunctions.clearCategoriesFromDb(manager);
        ApplicationUtilityFunctions.clearCalendarsFromDb(manager);
    }
}