package com.densoft.portfolio.service.blog;

import com.densoft.portfolio.dto.PostDTO;
import com.densoft.portfolio.dto.PostResponse;
import com.densoft.portfolio.model.Post;

public interface PostService {

    PostResponse getPosts(Integer pageNo, Integer PageSize, String sortBy, String sortDir);

    Post getPostById(Integer postId);

    Post getPostBySlug(String slug);

    Post createPost(PostDTO postDTO);

    Post togglePublishStatus(Integer postId);

    Post updatePost(PostDTO postDTO, Integer postId);

    void deletePost(int postId);

    Post publishOnMedium(Integer postId);
}
