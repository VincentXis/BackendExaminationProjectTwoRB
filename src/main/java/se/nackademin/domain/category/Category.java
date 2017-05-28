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


    // Constructors
    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }


    // Set
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Get
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
