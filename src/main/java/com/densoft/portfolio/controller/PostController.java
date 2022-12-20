package com.densoft.portfolio.controller;

import com.densoft.portfolio.dto.PostDTO;
import com.densoft.portfolio.model.Post;
import com.densoft.portfolio.service.blog.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public List<Post> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("{postSlug}")
    public Post getPost(@PathVariable("postSlug") String postSlug) {
        return postService.getPost(postSlug);
    }

    @PostMapping
    public Post createPost(@Valid @RequestBody PostDTO blogDTO) {
        return postService.createPost(blogDTO);
    }

    @DeleteMapping("{postId}")
    public String deletePost(@PathVariable("postId") Integer postId) {
        postService.deletePost(postId);
        return "post deleted successfully";
    }

    @GetMapping("publish-medium/{postId}")
    public Post publishOnMedium(@PathVariable("postId") Integer integer) {
        return postService.publishOnMedium(integer);
    }
}
