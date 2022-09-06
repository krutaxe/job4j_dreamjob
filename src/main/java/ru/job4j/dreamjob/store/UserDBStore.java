package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@ThreadSafe
public class UserDBStore {

    private static final Logger LOG_USER_DB = LoggerFactory.getLogger(
            UserDBStore.class.getName()
    );

    private static final String ADD_SQL = """
            INSERT INTO users (name, email, password) VALUES (?, ?, ?)
            """;

    private static final String FIND_ALL_SQL = """
            SELECT * FROM users
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT * FROM users WHERE id = ?
            """;

    private static final String FIND_BY_EMAIL_PWD = """
            SELECT * FROM users WHERE email = ? and password = ?
            """;

    private final BasicDataSource pool;

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public User findUserByEmailAndPwd(String email, String pwd) {
        User user = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_EMAIL_PWD)
        ) {
            ps.setString(1, email);
            ps.setString(2, pwd);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                     user = new User(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("email"),
                            it.getString("password"));
                }
            }
        } catch (Exception e) {
            LOG_USER_DB.error("Error findByEmailAndPwd", e);
        }
        return user;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_ALL_SQL)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    users.add(new User(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("email"),
                            it.getString("password")));
                }
            }
        } catch (Exception e) {
            LOG_USER_DB.error("Error findAll", e);
        }
        return users;
    }

    public Optional<User> add(User user) {
        Optional<User> rsl;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD_SQL,
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
            rsl = Optional.of(user);
        } catch (Exception e) {
            LOG_USER_DB.error("Error add", e);
            rsl = Optional.empty();
        }
        return rsl;
    }

    public User findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID_SQL)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new User(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("email"),
                            it.getString("password"));
                }
            }
        } catch (Exception e) {
            LOG_USER_DB.error("Error findById", e);
        }
        return null;
    }
}
