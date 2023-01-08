package com.densoft.portfolio.service.blog;

import com.densoft.portfolio.dto.PostCreateDTO;
import com.densoft.portfolio.dto.PostUpdateDTO;
import com.densoft.portfolio.dto.PostResponse;
import com.densoft.portfolio.exceptions.ApIException;
import com.densoft.portfolio.exceptions.ResourceNotFoundException;
import com.densoft.portfolio.model.Post;
import com.densoft.portfolio.repository.PostRepository;
import com.densoft.portfolio.repository.TagRepository;
import com.densoft.portfolio.restClient.RestService;
import com.densoft.portfolio.utils.AWSS3Util;
import com.densoft.portfolio.utils.Util;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
    public PostResponse getPosts(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        //create a pageable instance
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> postPage = postRepository.findAll(pageable);
        // get content from page object
        List<Post> posts = postPage.getContent();

        return new PostResponse(
                posts,
                postPage.getNumber(),
                postPage.getSize(),
                postPage.getTotalElements(),
                postPage.getTotalPages(),
                postPage.isLast()
        );
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
    public Post createPost(PostCreateDTO postDTO) {
        Optional<Post> post = postRepository.findPostByTitle(postDTO.getTitle());
        if (post.isPresent()) throw new ApIException("Post title is already used");

        Post newpost = new Post(postDTO.getTitle(), Util.generateSlug(postDTO.getTitle()));
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
    public Post updatePost(PostUpdateDTO postDTO, Integer postId) {
        Post post = getExistingPostById(postId);
        //check if title is taken
        Optional<Post> postWithTitle = postRepository.findPostWithMatchingTitle(postDTO.getTitle(), postId);
        if (postWithTitle.isPresent()) throw new ApIException("Post title is already taken");
        post.setTitle(postDTO.getTitle());
        post.setSlug(Util.generateSlug(postDTO.getTitle()));
        post.setContent(postDTO.getContent());
        //save tag
        post.setTag(util.generateTags(new String[]{postDTO.getTag()}).stream().toList().get(0));
        if (post.getBlogId() != null) {
            //update on dev blog
            restService.updatePost(postDTO, post.getBlogId(), false);
        }

        return postRepository.save(post);
    }


    @Override
    public void deletePost(int postId) {
        Post post = getExistingPostById(postId);
        //delete all images
        post.getImages().stream().forEach(image -> {
            AWSS3Util.deleteFile(image.getPath());
        });

        //un-publish post
        if (post.getBlogId() != null) {
            restService.updatePost(new PostUpdateDTO(post.getTitle(), post.getContent(), post.getTag().getName()), post.getBlogId(), true);
        }

        postRepository.deleteById(post.getId());
    }


    @Override
    public String publishOnDevBlog(Integer postId) {
        Post post = getExistingPostById(postId);
        if (post.getBlogId() != null) throw new ApIException("post already published");
        Map<String, String> response = restService.createPost(new PostUpdateDTO(post.getTitle(), post.getContent(), post.getTag().getName()));
        post.setBlogUrl(response.get("url"));
        post.setBlogId(Integer.parseInt(response.get("blogId")));
        Post updatedPost = postRepository.save(post);
        return updatedPost.getBlogUrl();
    }

    private Post getExistingPostById(Integer postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "Id", String.valueOf(postId)));
    }
}
