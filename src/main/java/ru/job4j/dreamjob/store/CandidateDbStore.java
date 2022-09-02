package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dreamjob.model.Candidate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CandidateDbStore {

    private static final Logger LOG_CANDIDATE_DB = LoggerFactory.getLogger(
            Candidate.class.getName()
    );
    private final BasicDataSource pool;

    private final String findAllSql = "SELECT * FROM candidate";

    private final String addSql = "INSERT INTO candidate(name, description, data, photo) "
            + "VALUES (?, ?, ?, ?)";

    private final String updateSql = "UPDATE candidate SET name = ?, description = ? WHERE id = ?";

    private final String findByIdSql = "SELECT * FROM candidate WHERE id = ?";

    public CandidateDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(findAllSql)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getObject("data", LocalDate.class)));
                }
            }
        } catch (Exception e) {
            LOG_CANDIDATE_DB.error("Error findAll", e);
        }
        return candidates;
    }

    public Candidate add(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(addSql,
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setObject(3, LocalDate.now());
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
             PreparedStatement ps = cn.prepareStatement(
                     updateSql)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
        } catch (Exception e) {
            LOG_CANDIDATE_DB.error("Error update", e);
        }
    }

    public Candidate findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(findByIdSql)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Candidate(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getObject("data", LocalDate.class));
                }
            }
        } catch (Exception e) {
            LOG_CANDIDATE_DB.error("Error findById", e);
        }
        return null;
    }
}
