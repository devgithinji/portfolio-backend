package com.densoft.portfolio.service.blog;

import com.densoft.portfolio.dto.PostCreateDTO;
import com.densoft.portfolio.dto.PostResponse;
import com.densoft.portfolio.dto.PostUpdateDTO;
import com.densoft.portfolio.model.Post;

import java.io.IOException;
import java.util.List;

public interface PostService {

    PostResponse getPosts(Integer pageNo,  String tag, String keyWord);

    List<Post> getRandomPosts(Integer limit);

    Post getPostById(Integer postId);

    Post getPostBySlug(String slug);

    Post createPost(PostCreateDTO postDTO) throws IOException;

    Post togglePublishStatus(Integer postId);

    Post updatePost(PostUpdateDTO postDTO, Integer postId) throws IOException;

    void deletePost(int postId);

    String publishOnDevBlog(Integer postId);
}
