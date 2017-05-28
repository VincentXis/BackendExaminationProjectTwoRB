package se.nackademin.domain.event;

import org.joda.time.DateTime;
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

    private DateTime startDate;
    private DateTime endDate;
    private DateTime startTime;
    private DateTime endTime;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Calendar calendar;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Category category;

    // Set
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }


    // Get
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }
}
