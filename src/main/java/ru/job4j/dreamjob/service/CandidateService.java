package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateDbStore;
import java.util.List;

@ThreadSafe
@Service
public class CandidateService {
    private final CandidateDbStore candidateDbStore;

    public CandidateService(CandidateDbStore candidateDbStore) {
        this.candidateDbStore = candidateDbStore;
    }

    public List<Candidate> findAll() {
        return candidateDbStore.findAll();
    }

    public void add(Candidate candidate) {
        candidateDbStore.add(candidate);
    }

    public Candidate findById(int id) {
        return candidateDbStore.findById(id);
    }

    public void update(Candidate candidate) {
        candidateDbStore.update(candidate);
    }
}
