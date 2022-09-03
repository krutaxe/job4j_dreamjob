package ru.job4j.dreamjob.store;


import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import java.util.List;
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
    public void whenFinaAllPost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        City city = new City(1, "KZN");
        Post post1 = new Post(1, "Java Job", "Super job", true, city);
        Post post2 = new Post(2, "PHP Job", "Norm job", false, city);
        Post post3 = new Post(3, "C# Job", "Good job", true, city);
        int size = store.findAll().size();
        store.add(post1);
        store.add(post2);
        store.add(post3);
        List<Post> postInDb = store.findAll();
        assertThat(postInDb.size()).isEqualTo(size + 3);

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