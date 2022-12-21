package com.densoft.portfolio.controller;

import com.densoft.portfolio.dto.PostDTO;
import com.densoft.portfolio.model.Post;
import com.densoft.portfolio.service.blog.PostService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("admin/{postId}")
    public Post getPostById(@PathVariable("postId") Integer postId) {
        return postService.getPostById(postId);
    }

    @GetMapping("{postSlug}")
    public Post getPost(@PathVariable("postSlug") String postSlug) {
        return postService.getPostBySlug(postSlug);
    }

    @PostMapping
    public Post createPost(@Valid @RequestBody PostDTO blogDTO) {
        return postService.createPost(blogDTO);
    }

    @PostMapping("toggle-publish/{postId}")
    public Post togglePublishStatus(@PathVariable("postId") Integer postId) {
        return postService.togglePublishStatus(postId);
    }

    @PutMapping("{postId}")
    public Post updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable("postId") Integer postId) {
        return postService.updatePost(postDTO, postId);
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
