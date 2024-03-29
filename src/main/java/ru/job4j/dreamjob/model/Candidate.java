package ru.job4j.dreamjob.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

public class Candidate {
    private int id;
    private String name;
    private String description;
    private LocalDate created;
    private byte[] photo;

    public Candidate() {
    }

    public Candidate(int id, String name, String description, LocalDate created, byte[] photo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
        this.photo = photo;
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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Candidate{"
               + "id=" + id
               + ", name='" + name + '\''
               + ", description='" + description + '\''
               + ", created=" + created
               + '}';
    }
}
