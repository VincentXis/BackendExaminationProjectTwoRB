package se.nackademin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import se.nackademin.domain.category.Category;
import se.nackademin.domain.event.Event;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class CalendarTest {
    private Calendar calendar;

    @Before
    public void setUp() throws Exception {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
        EntityManager manager = factory.createEntityManager();
        calendar = new Calendar(manager);
        calendar.fillDatabase();
    }

    @After
    public void tearDown() throws Exception {
        calendar.clearDb();
    }

    @Test
    public void getAllEvents() {
        List<Event> eventList = calendar.getAllEvents();
        assertTrue(eventList.size() > 0);
        eventList.forEach(System.out::print);
    }

    @Test
    public void getEventsByStartDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2017-05-31");
        List<Event> eventList = calendar.getEventsByStartDate(date);
        for (Event event : eventList) {
            assertTrue(event.getStartDate().equals(date));
        }
    }

    @Test
    public void getEventsByStartDateAndCategory() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2017-06-01");
        List<Event> eventList = calendar.getEventsByStartDateAndCategory(date, "Personal");
        for (Event event : eventList) {
            assertTrue(event.getStartDate().equals(date));
            assertTrue(event.getCategory().getName().equals("Personal"));
        }
    }

    @Test
    public void getEventsByCategory() {
        List<Event> eventList = calendar.getEventsByCategory("Personal");
        for (Event event : eventList) {
            assertTrue(event.getCategory().getName().equals("Personal"));
        }
    }

    @Test
    public void getAllCategories() {
        List<Category> categoryList = calendar.getAllCategories();
        assertTrue(categoryList.size() == 3);
        for (Category category : categoryList) {
            System.out.println(category.getName());
        }
    }

    @Test
    public void createCategoryAndAddEventToIt() {
        calendar.persistNewCategory(new Category("Wawa"));
        calendar.persistNewEventToCategory("Wawa", "Fun Time", "Super happy fun time, gogo powerrangers", "2017-06-20", "2017-06-25");
        Category category = calendar.getCategoryMatchingName("Wawa");
        assertTrue(category.getName().equals("Wawa"));
        assertTrue(category.getEventList().get(0).getName().equals("Fun Time"));
    }

}