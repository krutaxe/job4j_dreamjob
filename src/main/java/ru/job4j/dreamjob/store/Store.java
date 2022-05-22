package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Store {
    private static final Store INST = new Store();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private Store() {
        posts.put(1, new Post(1, "Junior Java Job",
                "SberData – департамент по управлению данными всего Сбербанка.", LocalDate.now()));
        posts.put(2, new Post(2, "Middle Java Job",
                "Инженер по автомат. тестированию, Средний (Middle) • Git • Java • Appium • Selenium ", LocalDate.now()));
        posts.put(3, new Post(3, "Senior Java Job",
                "SMSAPI - это команда, которая отвечает за разработку верификационных сервисов VK. ", LocalDate.now()));
        candidates.put(1, new Candidate(1, "Junior Java"));
        candidates.put(2, new Candidate(2, "Middle Java"));
        candidates.put(3, new Candidate(3, "Senior Java"));
    }

    public static Store instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

}
