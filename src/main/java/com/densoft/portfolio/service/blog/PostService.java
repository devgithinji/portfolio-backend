package com.densoft.portfolio.service.blog;

import com.densoft.portfolio.dto.PostDTO;
import com.densoft.portfolio.model.Post;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface PostService {

    Post createPost(PostDTO postDTO) throws JsonProcessingException;

    Post publishOnMedium(Integer id) throws JsonProcessingException;
}
