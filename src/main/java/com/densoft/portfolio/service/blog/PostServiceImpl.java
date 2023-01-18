package com.densoft.portfolio.service.blog;

import com.densoft.portfolio.dto.PostCreateDTO;
import com.densoft.portfolio.dto.PostResponse;
import com.densoft.portfolio.dto.PostUpdateDTO;
import com.densoft.portfolio.exceptions.ApIException;
import com.densoft.portfolio.exceptions.ResourceNotFoundException;
import com.densoft.portfolio.model.Post;
import com.densoft.portfolio.repository.ImageRepository;
import com.densoft.portfolio.repository.PostRepository;
import com.densoft.portfolio.repository.TagRepository;
import com.densoft.portfolio.restClient.RestService;
import com.densoft.portfolio.utils.AppConstants;
import com.densoft.portfolio.utils.CloudinaryUtil;
import com.densoft.portfolio.utils.Util;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final ImageRepository imageRepository;

    private final TagRepository tagRepository;

    private final RestService restService;

    private final Util util;


    public PostServiceImpl(PostRepository postRepository, ImageRepository imageRepository, TagRepository tagRepository, RestService restService, Util util) {
        this.postRepository = postRepository;
        this.imageRepository = imageRepository;
        this.tagRepository = tagRepository;
        this.restService = restService;
        this.util = util;
    }

    @Override
    public PostResponse getPosts(Integer pageNo, String tag, String keyWord) {
        //create a pageable instance
        Sort sort = Sort.by(AppConstants.DEFAULT_SORT_BY).descending();
        Pageable pageable = PageRequest.of(pageNo, Integer.parseInt(AppConstants.DEFAULT_PAGE_SIZE), sort);
        Page<Post> postPage;

        if (keyWord != null && !keyWord.isBlank()) {
            postPage = postRepository.findPostsByKeyWord(pageable, keyWord);
        } else {

            if (tag.equalsIgnoreCase(AppConstants.DEFAULT_CATEGORY)) {
                postPage = postRepository.findAll(pageable);
            } else {
                postPage = postRepository.findPostsByTag(pageable, tag);
            }
        }


        // get content from page object
        List<Post> posts = postPage.getContent();

        return new PostResponse(posts, postPage.getNumber(), postPage.getSize(), postPage.getTotalElements(), postPage.getTotalPages(), postPage.isLast());
    }

    @Override
    public List<Post> getRandomPosts(Integer limit) {
        return postRepository.findRandomPosts(limit);
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
        String filePath = CloudinaryUtil.UploadFile(postDTO.getImage(), "posts", true);

        Post newpost = new Post(postDTO.getTitle(), Util.generateSlug(postDTO.getTitle()));
        newpost.setPublished(true);
        newpost.setImage(filePath);
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
    public Post updatePost(PostUpdateDTO postDTO, Integer postId) throws IOException {
        Post post = getExistingPostById(postId);
        //check if title is taken
        Optional<Post> postWithTitle = postRepository.findPostWithMatchingTitle(postDTO.getTitle(), postId);
        if (postWithTitle.isPresent()) throw new ApIException("Post title is already taken");

        if (postDTO.getImage() != null) {
            if (post.getImage() != null) {
                CloudinaryUtil.deleteFile(post.getImage());
            }

            String filePath = CloudinaryUtil.UploadFile(postDTO.getImage(), "posts", true);
            post.setImage(filePath);
        }
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
            CloudinaryUtil.deleteFile(image.getPath());
        });

        imageRepository.deleteAll(post.getImages());

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
