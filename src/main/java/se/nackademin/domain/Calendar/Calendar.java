package se.nackademin.domain.Calendar;

import se.nackademin.domain.event.Event;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Calendar {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Event> eventList;

    // Constructor
    public Calendar() {
    }

    public Calendar(String name) {
        this.name = name;
    }

    // set
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    // Get
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Event> getEventList() {
        return eventList;
    }
}
