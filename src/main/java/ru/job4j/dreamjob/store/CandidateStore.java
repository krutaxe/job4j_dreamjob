package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CandidateStore {
    private static final CandidateStore INST = new CandidateStore();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    public CandidateStore() {
        candidates.put(1, new Candidate(1, "Dima",
                "Java Core, SQL", LocalDate.now()));
        candidates.put(2, new Candidate(2, "Misha",
                "JavaScript, Angular", LocalDate.now()));
        candidates.put(3, new Candidate(3, "Alex",
                "Spring (Spring Boot, Spring JPA, Spring Cloud)", LocalDate.now()));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
