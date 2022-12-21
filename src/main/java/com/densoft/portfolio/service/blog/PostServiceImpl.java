package com.densoft.portfolio.service.blog;

import com.densoft.portfolio.dto.PostDTO;
import com.densoft.portfolio.exceptions.ApIException;
import com.densoft.portfolio.exceptions.ResourceNotFoundException;
import com.densoft.portfolio.model.Post;
import com.densoft.portfolio.repository.PostRepository;
import com.densoft.portfolio.repository.TagRepository;
import com.densoft.portfolio.restClient.RestService;
import com.densoft.portfolio.utils.Util;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final TagRepository tagRepository;

    private final RestService restService;

    private final Util util;


    public PostServiceImpl(PostRepository postRepository, TagRepository tagRepository, RestService restService, Util util) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
        this.restService = restService;
        this.util = util;
    }

    @Override
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(Integer postId) {
        return getExistingPostById(postId);
    }

    @Override
    public Post getPostBySlug(String slug) {
        return postRepository.findPostBySlug(slug).orElseThrow(() -> new ResourceNotFoundException("post", "slug", slug));
    }


    @Override
    public Post createPost(PostDTO postDTO) {
        Optional<Post> post = postRepository.findPostByTitle(postDTO.getTitle());
        if (post.isPresent()) throw new ApIException("Post title is already used");

        Post newpost = new Post(postDTO.getTitle(), Util.generateSlug(postDTO.getTitle()), postDTO.getContent());
        newpost.setPublished(true);
        //save tag
        newpost.setTag(util.generateTags(new String[]{postDTO.getTag()}).stream().toList().get(0));
        return postRepository.save(newpost);
    }

    @Override
    public Post togglePublishStatus(Integer postId) {
        Post post = getExistingPostById(postId);
        post.setPublished(!post.getPublished());
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(PostDTO postDTO, Integer postId) {
        Post post = getExistingPostById(postId);
        //check if title is taken
        Optional<Post> postWithTitle = postRepository.findPostWithMatchingTitle(postDTO.getTitle(), postId);
        if (postWithTitle.isPresent()) throw new ApIException("Post title is already taken");
        post.setTitle(postDTO.getTitle());
        post.setSlug(Util.generateSlug(postDTO.getTitle()));
        post.setContent(post.getContent());
        //save tag
        post.setTag(util.generateTags(new String[]{postDTO.getTag()}).stream().toList().get(0));
        return postRepository.save(post);
    }


    @Override
    public void deletePost(int postId) {
        Post post = getExistingPostById(postId);
        //delete all images

        postRepository.deleteById(post.getId());
    }


    @Override
    public Post publishOnMedium(Integer postId) {
        Post post = getExistingPostById(postId);
//        PostResponse postResponse = restService.createPost(new PostDTO(post.getTitle(), "html", post.getContent(), post.getTagsList()));
//        post.setMediumUrl(postResponse.getData().getUrl());
        return postRepository.save(post);
    }

    private Post getExistingPostById(Integer postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "Id", String.valueOf(postId)));
    }
}
