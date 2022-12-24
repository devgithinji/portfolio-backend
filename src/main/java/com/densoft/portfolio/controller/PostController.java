package com.densoft.portfolio.controller;

import com.densoft.portfolio.dto.PostDTO;
import com.densoft.portfolio.dto.PostResponse;
import com.densoft.portfolio.model.Post;
import com.densoft.portfolio.service.blog.PostService;
import com.densoft.portfolio.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse getPosts(@RequestParam(value = "PageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNo,
                                 @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
                                 @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                 @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir) {
        return postService.getPosts(pageNo, pageSize, sortBy, sortDir);
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
