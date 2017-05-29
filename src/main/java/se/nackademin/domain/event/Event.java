package se.nackademin.domain.event;

import se.nackademin.domain.category.Category;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table
public class Event {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;

    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @ManyToOne
    private Category category;


    // Constructors
    public Event() {
    }

    public Event(String name, String description, String startDate, String endDate) {
        this.name = name;
        this.description = description;
        this.startDate = parseDateFromString(startDate);
        this.endDate = parseDateFromString(endDate);
    }

    public Event(String name, String description, String startDate, String endDate, Category category) {
        this.name = name;
        this.description = description;
        this.startDate = parseDateFromString(startDate);
        this.endDate = parseDateFromString(endDate);
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

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Category getCategory() {
        return category;
    }

    private Date parseDateFromString(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return String.format("Id: %d\nName: %s\nDescription: %s\nCategory: %s\nStart date: %s\nEnd date: %s\n",
                id, name, description, category, sdf.format(startDate), sdf.format(startDate));
    }
}
