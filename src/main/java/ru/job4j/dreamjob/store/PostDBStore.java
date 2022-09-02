package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostDBStore {

    private static final Logger LOG_POST_DB = LoggerFactory.getLogger(
            PostDBStore.class.getName()
    );

    private static final String FIND_ALL_SQL = "SELECT * FROM post";

    private static final String ADD_SQL = "INSERT INTO post(name) VALUES (?)";

    private static final String UPDATE_SQL = "UPDATE post SET name = ?, description = ?, "
            + "city_id = ? WHERE id = ?";

    private static final String FIND_BY_ID_SQL = "SELECT * FROM post WHERE id = ?";

    private final BasicDataSource pool;

    public PostDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_ALL_SQL)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getObject("city", City.class),
                            it.getObject("data", LocalDate.class)));
                }
            }
        } catch (Exception e) {
            LOG_POST_DB.error("Error findAll", e);
        }
        return posts;
    }

    public Post add(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD_SQL,
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setObject(3, LocalDate.now());
            ps.setBoolean(4, post.isVisible());
            ps.setObject(5, post.getCity());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG_POST_DB.error("Error add", e);
        }
        return post;
    }

    public void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE_SQL)) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setObject(3, post.getCity().getId());
        } catch (Exception e) {
            LOG_POST_DB.error("Error update", e);
        }
    }

    public Post findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID_SQL)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Post(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getObject("city", City.class),
                            it.getObject("data", LocalDate.class));
                }
            }
        } catch (Exception e) {
            LOG_POST_DB.error("Error findById", e);
        }
        return null;
    }
}
