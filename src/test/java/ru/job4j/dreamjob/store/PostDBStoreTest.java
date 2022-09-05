package ru.job4j.dreamjob.store;

import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import static org.assertj.core.api.Assertions.assertThat;

class PostDbStoreTest {

    @Test
    public void whenCreatePostGetName() {
        PostDbStore store = new PostDbStore(new Main().loadPool());
        Post post = new Post(0, "Java job", "Super job",
                true, new City());
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName()).isEqualTo(post.getName());
    }

    @Test
    public void whenCreatePostGetDesc() {
        PostDbStore store = new PostDbStore(new Main().loadPool());
        Post post1 = new Post(0, "Java job", "Super job",
                true, new City());
        Post post2 = new Post(0, "Java job", "Good job",
                true, new City());
        store.add(post1);
        store.add(post2);
        Post postInDb = store.findById(post2.getId());
        assertThat(postInDb.getDescription()).isEqualTo(post2.getDescription());
    }

    @Test
    public void whenUpdatePostDesc() {
        PostDbStore store = new PostDbStore(new Main().loadPool());
        Post post1 = new Post(1, "Java job", "Super job",
                true, new City());
        Post post2 = new Post(2, "PHP job", "Good job",
                true, new City());
        store.add(post1);
        store.add(post2);
        post2.setDescription("Fire");
        store.update(post2);
        Post postInDb = store.findById(post2.getId());
        assertThat(postInDb.getDescription()).isEqualTo("Fire");
    }
}