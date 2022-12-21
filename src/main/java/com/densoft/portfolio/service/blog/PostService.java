package com.densoft.portfolio.service.blog;

import com.densoft.portfolio.dto.PostDTO;
import com.densoft.portfolio.model.Post;

import java.util.List;

public interface PostService {

    List<Post> getPosts();

    Post getPostById(Integer postId);

    Post getPostBySlug(String slug);

    Post createPost(PostDTO postDTO);

    Post togglePublishStatus(Integer postId);

    Post updatePost(PostDTO postDTO, Integer postId);

    void deletePost(int postId);

    Post publishOnMedium(Integer postId);
}
