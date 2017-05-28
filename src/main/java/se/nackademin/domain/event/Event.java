package se.nackademin.domain.event;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import se.nackademin.domain.Calendar.Calendar;
import se.nackademin.domain.category.Category;

import javax.persistence.*;

@Entity
@Table
public class Event {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;

    private LocalDate startDate;
    private LocalDate endDate;

    private LocalTime startTime;
    private LocalTime endTime;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Calendar calendar;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Category category;

    // Constructors
    public Event() {
    }

    public Event(String name, String description, String startDate, String endDate, String startTime, String endTime) {
        this.name = name;
        this.description = description;
        this.startDate = LocalDate.parse(startDate);
        this.endDate = LocalDate.parse(endDate);
        this.startTime = LocalTime.parse(startTime, DateTimeFormat.forPattern("HH:mm"));
        this.endTime = LocalTime.parse(endTime, DateTimeFormat.forPattern("HH:mm"));
    }

    public Event(String name, String description, String startDate, String endDate, String startTime, String endTime, Category category) {
        this.name = name;
        this.description = description;
        this.startDate = LocalDate.parse(startDate);
        this.endDate = LocalDate.parse(endDate);
        this.startTime = LocalTime.parse(startTime, DateTimeFormat.forPattern("HH:mm"));
        this.endTime = LocalTime.parse(endTime, DateTimeFormat.forPattern("HH:mm"));
        this.category = category;
    }


    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormat.shortTime();
        return String.format("Id: %d\nName: %s\nDescription: %s\nCategory: %s\nStart date: %s\nEnd date: %s\nStart time: %s\nEnd time: %s\nCalendar: %s\n",
                id, name, description, category, startDate, endDate, dtf.print(startTime), dtf.print(endTime), calendar
        );
    }
}
