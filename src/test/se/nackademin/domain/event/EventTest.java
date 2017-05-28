package se.nackademin.domain.event;

import javafx.util.converter.LocalTimeStringConverter;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EventTest {
    private EntityManager manager;

    @Before
    public void setUp() throws Exception {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
        manager = factory.createEntityManager();
        manager.getTransaction().begin();
    }

    @Test
    public void createAnEvent(){
        List<Event> events = new ArrayList<>();
        events.add(new Event("Dentist", "dentist appointment","2017-06-01","2017-06-01", "10:00", "10:00"));
        events.add(new Event("Dentist", "dentist appointment", "2017-06-01","2017-06-01", "10:00", "10:00"));
        events.add(new Event("Dentist", "dentist appointment", "2017-06-01","2017-06-01", "10:00", "10:00"));

        events.forEach(System.out::println);
    }

    // test helper functions


}