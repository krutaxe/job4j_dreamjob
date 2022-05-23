package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Post;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PostStore {
    private static final PostStore INST = new PostStore();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    public PostStore() {
        posts.put(1, new Post(1, "Junior Java Job",
                "SQL и принципы проектирования баз данных", LocalDate.now()));
        posts.put(2, new Post(2, "Middle Java Job",
                "JavaScript. Знакомство с Angular будет большим плюсом", LocalDate.now()));
        posts.put(3, new Post(3, "Senior Java Job",
                "знание Spring (Spring Boot, Spring JPA, Spring Cloud)", LocalDate.now()));
    }

    public static PostStore instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public void add(Post post) {
        posts.put(post.getId(), post);
    }
}
