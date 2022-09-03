package ru.job4j.dreamjob.store;


import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import static org.assertj.core.api.Assertions.*;


public class PostDBStoreTest {
    @Test
    public void whenCreatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(0, "Java Job!", "Super job", true, new City());
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName()).isEqualTo(post.getName());
    }

    @Test
    public void whenCreatedPostWithCity() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(0, "Java Job!", "Super job",
                true, new City(1, "KZN"));
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getCity().getId()).isEqualTo(post.getCity().getId());
    }


    @Test
    public void whenUpdatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(0, "Java Job", "Super job", true, new City());
        store.add(post);
        post.setDescription("Good job");
        store.update(post);
        store.add(post);
        Post expected = new Post(0, "Java Job", "Good job", true, new City());
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getDescription()).isEqualTo(expected.getDescription());
    }
}