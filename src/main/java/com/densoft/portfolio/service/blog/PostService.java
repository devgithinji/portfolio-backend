package com.densoft.portfolio.service.blog;

import com.densoft.portfolio.dto.PostDTO;
import com.densoft.portfolio.model.Post;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface PostService {

    List<Post> getPosts();


    Post getPost(String slug);

    void deletePost(int postId);

    Post createPost(PostDTO postDTO);

    Post publishOnMedium(Integer id) ;
}
