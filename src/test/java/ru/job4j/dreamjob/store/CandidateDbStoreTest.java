package ru.job4j.dreamjob.store;

import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class CandidateDbStoreTest {
    @Test
    public void whenCreateCandidateCreate() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        Candidate candidate = new Candidate(0, "Dima", "Java dev", LocalDate.now());
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getCreated()).isEqualTo(candidate.getCreated());
    }

    @Test
    public void whenCreateCandidateName() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        Candidate candidate1 = new Candidate(0, "Dima", "Java dev", LocalDate.now());
        Candidate candidate2 = new Candidate(0, "Roma", "PHP dev", LocalDate.now());
        store.add(candidate1);
        store.add(candidate2);
        Candidate candidateInDb = store.findById(candidate2.getId());
        assertThat(candidateInDb.getName()).isEqualTo(candidate2.getName());
    }

    @Test
    public void whenUpdateCandidateName() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        Candidate candidate1 = new Candidate(1, "Dima", "Java dev", LocalDate.now());
        Candidate candidate2 = new Candidate(2, "Roma", "PHP dev", LocalDate.now());
        store.add(candidate1);
        store.add(candidate2);
        candidate2.setName("Petr");
        store.update(candidate2);
        Candidate candidateInDb = store.findById(candidate2.getId());
        assertThat(candidateInDb.getName()).isEqualTo("Petr");
    }
}