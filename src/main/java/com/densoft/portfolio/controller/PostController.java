package com.densoft.portfolio.controller;

import com.densoft.portfolio.dto.PostCreateDTO;
import com.densoft.portfolio.dto.PostUpdateDTO;
import com.densoft.portfolio.dto.PostResponse;
import com.densoft.portfolio.model.Post;
import com.densoft.portfolio.service.blog.PostService;
import com.densoft.portfolio.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public PostResponse getPosts(@RequestParam(value = "keyWord", required = false) String keyWord,
                                 @RequestParam(value = "category", defaultValue = AppConstants.DEFAULT_CATEGORY, required = false) String category,
                                 @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNo) {
        return postService.getPosts(pageNo, category, keyWord);
    }

    @GetMapping("random")
    public List<Post> getRandomPosts(@RequestParam(value = "limit", defaultValue = "7", required = false) int limit) {
        return postService.getRandomPosts(limit);
    }

    @GetMapping("admin/{postId}")
    public Post getPostById(@PathVariable("postId") Integer postId) {
        return postService.getPostById(postId);
    }

    @GetMapping("{postSlug}")
    public Post getPost(@PathVariable("postSlug") String postSlug) {
        return postService.getPostBySlug(postSlug);
    }


    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(@Valid PostCreateDTO blogDTO) throws IOException {
        return postService.createPost(blogDTO);
    }

    @PostMapping("toggle-publish/{postId}")
    public Post togglePublishStatus(@PathVariable("postId") Integer postId) {
        return postService.togglePublishStatus(postId);
    }

    @PutMapping(value = "{postId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Post updatePost(@Valid PostUpdateDTO postDTO, @PathVariable("postId") Integer postId) throws IOException {
        return postService.updatePost(postDTO, postId);
    }

    @DeleteMapping("{postId}")
    public String deletePost(@PathVariable("postId") Integer postId) {
        postService.deletePost(postId);
        return "post deleted successfully";
    }

    @GetMapping("publish-on-dev-blog/{postId}")
    public ResponseEntity<Object> publishOnDevBlog(@PathVariable("postId") Integer integer) {
        String url = postService.publishOnDevBlog(integer);
        Map<String, String> response = new HashMap<>();
        response.put("message", "post published successfully");
        response.put("url", url);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
