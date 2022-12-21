package com.densoft.portfolio.service.blog;

import com.densoft.portfolio.dto.PostDTO;
import com.densoft.portfolio.exceptions.ApIException;
import com.densoft.portfolio.exceptions.ResourceNotFoundException;
import com.densoft.portfolio.model.Post;
import com.densoft.portfolio.repository.PostRepository;
import com.densoft.portfolio.repository.TagRepository;
import com.densoft.portfolio.restClient.RestService;
import com.densoft.portfolio.utils.Util;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final TagRepository tagRepository;

    private final RestService restService;

    private final Util util;

    private final ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, TagRepository tagRepository, RestService restService, Util util, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
        this.restService = restService;
        this.util = util;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Post> getPosts() {
        return postRepository.findAll();
    }
    @Override
    public Post createPost(PostDTO postDTO) {
        Optional<Post> post = postRepository.findPostByTitle(postDTO.getTitle());
        if (post.isPresent()) throw new ApIException("Post title is already used");

        Post newpost = new Post(postDTO.getTitle(),Util.generateSlug(postDTO.getTitle()), postDTO.getContent());


//        for (String tag : postDTO.getTags()) {
//            newpost.addTag(util.generateTags(tag));
//        }
        return postRepository.save(newpost);
    }

    @Override
    public Post getPost(String slug) {
        return postRepository.findPostBySlug(slug).orElseThrow(() -> new ResourceNotFoundException("post", "slug", slug));
    }

    @Override
    public void deletePost(int postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", String.valueOf(postId)));
        postRepository.deleteById(post.getId());
    }




    @Override
    public Post publishOnMedium(Integer id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id.toString()));
//        PostResponse postResponse = restService.createPost(new PostDTO(post.getTitle(), "html", post.getContent(), post.getTagsList()));
//        post.setMediumUrl(postResponse.getData().getUrl());
        return postRepository.save(post);
    }
}
