package com.densoft.portfolio.service.blog;

import com.densoft.portfolio.dto.PostDTO;
import com.densoft.portfolio.exceptions.ApIException;
import com.densoft.portfolio.exceptions.ResourceNotFoundException;
import com.densoft.portfolio.model.Post;
import com.densoft.portfolio.repository.PostRepository;
import com.densoft.portfolio.restClient.PostResponse;
import com.densoft.portfolio.restClient.RestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private RestService restService;

    @Override
    public Post createPost(PostDTO postDTO) throws JsonProcessingException {
        Optional<Post> post = postRepository.findPostByTitle(postDTO.getTitle());
        if (post.isEmpty()) {
            return postRepository.save(Post.createPost(postDTO));
        }
        throw new ApIException("Post title already used");

    }

    @Override
    public Post publishOnMedium(Integer id) throws JsonProcessingException {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id.toString()));
        PostResponse postResponse = restService.createPost(new PostDTO(post.getTitle(), "html", post.getContent(), post.getTagsList()));
        post.setMediumUrl(postResponse.getData().getUrl());
        return postRepository.save(post);
    }
}
