package ru.job4j.dreamjob.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Post implements Serializable {
    private int id;
    private final String name;
    private final String description;
    private final LocalDate created;
    private boolean visible;
    private City city;

    public Post(int id, String name, String description, LocalDate created) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
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
}
