package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class CandidateDbStore {

    private static final Logger LOG_CANDIDATE_DB = LoggerFactory.getLogger(
            Candidate.class.getName()
    );

    private static final String FIND_ALL_SQL = """
            SELECT * FROM candidate ORDER BY id
            """;

    private static final String ADD_SQL = """
            INSERT INTO candidate(name, description, created, photo) VALUES (?, ?, ?, ?)
            """;

    private static final String UPDATE_SQL = """
            UPDATE candidate SET name = ?, description = ?, photo = ? WHERE id = ?
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT * FROM candidate WHERE id = ?
            """;

    private final BasicDataSource pool;

    public CandidateDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_ALL_SQL)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getDate("created").toLocalDate(),
                            it.getBytes("photo")));
                }
            }
        } catch (Exception e) {
            LOG_CANDIDATE_DB.error("Error findAll", e);
        }
        return candidates;
    }

    public Candidate add(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD_SQL,
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setDate(3, Date.valueOf(LocalDate.now()));
            ps.setBytes(4, candidate.getPhoto());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG_CANDIDATE_DB.error("Error add", e);
        }
        return candidate;
    }

    public void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE_SQL,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setBytes(3, candidate.getPhoto());
            ps.setInt(4, candidate.getId());

            ps.executeUpdate();
        } catch (Exception e) {
            LOG_CANDIDATE_DB.error("Error update", e);
        }
    }

    public Candidate findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID_SQL)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Candidate(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getDate("created").toLocalDate(),
                            it.getBytes("photo"));
                }
            }
        } catch (Exception e) {
            LOG_CANDIDATE_DB.error("Error findById", e);
        }
        return null;
    }
}
