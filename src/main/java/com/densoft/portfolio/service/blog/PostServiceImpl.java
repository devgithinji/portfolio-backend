package com.densoft.portfolio.service.blog;

import com.densoft.portfolio.dto.PostDTO;
import com.densoft.portfolio.exceptions.ApIException;
import com.densoft.portfolio.exceptions.ResourceNotFoundException;
import com.densoft.portfolio.model.Post;
import com.densoft.portfolio.model.Tag;
import com.densoft.portfolio.repository.PostRepository;
import com.densoft.portfolio.repository.TagRepository;
import com.densoft.portfolio.restClient.PostResponse;
import com.densoft.portfolio.restClient.RestService;
import com.densoft.portfolio.utils.Util;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private RestService restService;

    @Override
    public Post createPost(PostDTO postDTO) throws JsonProcessingException {
        Optional<Post> post = postRepository.findPostByTitle(postDTO.getTitle());
        if (post.isEmpty()) {
            Post newpost = new Post();
            newpost.setTitle(postDTO.getTitle());
            newpost.setSlug(Util.generateSlug(postDTO.getTitle()));
            newpost.setContent(postDTO.getContent());
            newpost.setPublished(true);

            for (String tag : postDTO.getTags()) {
                newpost.addTag(generateTag(tag));
            }
            return postRepository.save(newpost);
        }
        throw new ApIException("Post title is already used");

    }

    public Tag generateTag(String matchTag) {
        return tagRepository.findAll().stream().filter(tag -> tag.getName().equalsIgnoreCase(matchTag)).findFirst().get();
    }

    @Override
    public Post publishOnMedium(Integer id) throws JsonProcessingException {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id.toString()));
//        PostResponse postResponse = restService.createPost(new PostDTO(post.getTitle(), "html", post.getContent(), post.getTagsList()));
//        post.setMediumUrl(postResponse.getData().getUrl());
        return postRepository.save(post);
    }
}
