package ru.job4j.dreamjob.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Post implements Serializable {
    private int id;
    private String name;
    private String description;
    private LocalDate created = LocalDate.now();
    private boolean visible;
    private City city;

    public Post(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Post(int id, String name, String description, boolean visible, City city) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.visible = visible;
        this.city = city;
    }

    public City getCity() {
        return city;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getCreated() {
        return created;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Post{"
              +  "id=" + id
              +  ", name='" + name + '\''
              +  ", description='" + description + '\''
              +  ", created=" + created
              +  ", visible=" + visible
              +  ", city=" + city
              +  '}';
    }
}
