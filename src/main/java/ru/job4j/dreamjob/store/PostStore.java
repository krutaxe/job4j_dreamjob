package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class PostStore {
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger ids = new AtomicInteger(3);

    private PostStore() {
        posts.put(1, new Post(1, "Junior Java Job",
                "SQL и принципы проектирования баз данных", LocalDate.now()));
        posts.put(2, new Post(2, "Middle Java Job",
                "JavaScript. Знакомство с Angular будет большим плюсом", LocalDate.now()));
        posts.put(3, new Post(3, "Senior Java Job",
                "знание Spring (Spring Boot, Spring JPA, Spring Cloud)", LocalDate.now()));
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public void add(Post post) {
        post.setId(ids.incrementAndGet());
        posts.put(post.getId(), post);
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public Post update(Post post) {
        return posts.replace(post.getId(), post);
    }
}
