package com.densoft.portfolio.service.blog;

import com.densoft.portfolio.dto.PostCreateDTO;
import com.densoft.portfolio.dto.PostUpdateDTO;
import com.densoft.portfolio.dto.PostResponse;
import com.densoft.portfolio.model.Post;

public interface PostService {

    PostResponse getPosts(Integer pageNo, Integer PageSize, String sortBy, String sortDir);

    Post getPostById(Integer postId);

    Post getPostBySlug(String slug);

    Post createPost(PostCreateDTO postDTO);

    Post togglePublishStatus(Integer postId);

    Post updatePost(PostUpdateDTO postDTO, Integer postId);

    void deletePost(int postId);

    String publishOnDevBlog(Integer postId);
}
