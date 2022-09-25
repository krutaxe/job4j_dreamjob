package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PostControllerTest {
    @Test
    public void whenPosts() {

        List<Post> posts = Arrays.asList(
                new Post(1, "New post", "post", true,
                         new City()),
                new Post(2, "New post", "post", true,
                        new City())
        );

        List<Post> wrongData = Arrays.asList(
                new Post(1, "New post", "post", true,
                        new City()),
                new Post(2, "New post", "post", true,
                        new City())
        );

        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);

        when(postService.findAll()).thenReturn(posts);

        PostController postController = new PostController(
                postService,
                cityService
        );

        String page = postController.posts(model, session);
        verify(model).addAttribute("posts", wrongData);
        assertThat(page, is("posts"));
    }

    @Test
    public void whenFormAddPost() {
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        HttpSession httpSession = mock(HttpSession.class);
        PostController postController = new PostController(postService, cityService);

        String page = postController.formAddPost(model, httpSession);
        assertThat(page, is("addPost"));
    }

    @Test
    public void whenCreatePost() {
        Post input = new Post(1, "New post", "post", false,
                new City());
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.createPost(input);
        verify(postService).add(input);
        assertThat(page, is("redirect:/posts"));
    }

    @Test
    public void whenUpdatePost() {
        Post post = new Post(1, "post", "post", true,
                new City());
        Post newPost = new Post(1, "NewPost", "NewPost", false,
                new City());
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        postController.createPost(post);
        verify(postService).add(post);
        String page = postController.updatePost(newPost);
        verify(postService).update(newPost);
        assertThat(page, is("redirect:/posts"));
    }

    @Test
    public void whenFormUpdatePost() {
        Post post = new Post(1, "Post", "post", true,
                new City());
        List<Post> posts = List.of(post);
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(postService, cityService);
        when(postService.findById(0)).thenReturn(posts.get(0));

        String page = postController.formUpdatePost(model, 0);
        verify(model).addAttribute("post", post);
        assertThat(page, is("updatePost"));
    }
}