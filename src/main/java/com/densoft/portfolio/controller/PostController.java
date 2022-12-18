package com.densoft.portfolio.controller;

import com.densoft.portfolio.dto.PostDTO;
import com.densoft.portfolio.model.Post;
import com.densoft.portfolio.service.blog.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public Post createPost(@Valid @RequestBody PostDTO blogDTO) throws JsonProcessingException {
        return postService.createPost(blogDTO);
    }

    @GetMapping("publish-medium/{postId}")
    public Post publishOnMedium(@PathVariable("postId") Integer integer) throws JsonProcessingException {
        return postService.publishOnMedium(integer);
    }
}
