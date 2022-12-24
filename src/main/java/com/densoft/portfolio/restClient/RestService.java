package com.densoft.portfolio.restClient;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;


@Component
public class RestService {

    @Autowired
    private WebClient webClient;

    private String userId;

    @PostConstruct
    private void postConstruct() {
//        getUserId();
    }

//    public UserResponse getUserId() {
//        UserResponse res = webClient.get().uri("/me").retrieve().bodyToMono(UserResponse.class).block();
//        userId = res.data.getId();
//        return res;
//    }
//
//    public PostResponse createPost(PostDTO postDTO) {
//
//        return webClient.post().uri("/users/{userId}/posts", userId).bodyValue(postDTO).retrieve().bodyToMono(PostResponse.class).block();
//    }
}
