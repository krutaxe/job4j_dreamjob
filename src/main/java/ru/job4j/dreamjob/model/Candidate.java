package ru.job4j.dreamjob.model;

import java.time.LocalDate;
import java.util.Objects;

public class Candidate {
    private int id;
    private final String name;
    private final String description;
    private final LocalDate created = LocalDate.now();

    private byte[] photo;

    public Candidate(int id, String name, String description, LocalDate created) {
        this.id = id;
        this.name = name;
        this.description = description;
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
}
