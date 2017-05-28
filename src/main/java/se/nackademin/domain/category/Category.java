package se.nackademin.domain.category;

import se.nackademin.domain.event.Event;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Event> eventList;

    // Constructors
    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }


    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    @Override
    public String toString() {
        return name;
    }
}
