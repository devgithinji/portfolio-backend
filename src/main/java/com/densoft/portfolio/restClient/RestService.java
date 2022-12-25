package com.densoft.portfolio.restClient;


import com.densoft.portfolio.dto.PostDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;


@Component
public class RestService {

    private final WebClient webClient;

    public RestService(WebClient webClient) {
        this.webClient = webClient;
    }


    public Map<String, String> createPost(PostDTO postDTO) {
        Map<String, Object> requestBody = getRequestBody(postDTO, false);
        Map response = webClient.post().uri("/articles").bodyValue(requestBody).retrieve().bodyToMono(Map.class).block();
        Map<String, String> data = new HashMap<>();
        data.put("blogId", response.get("id").toString());
        data.put("url", response.get("url").toString());
        return data;
    }

    public void updatePost(PostDTO postDTO, Integer postId, boolean unPublish) {
        Map<String, Object> requestBody = getRequestBody(postDTO, unPublish);
        webClient.put().uri("/articles/" + postId).bodyValue(requestBody).retrieve().bodyToMono(Map.class).block();
    }

    public void unPublishArticle(Integer postId) {
        webClient.put().uri("/articles/" + postId + "/unpublish").retrieve().bodyToMono(Map.class).block();
    }

    private static Map<String, Object> getRequestBody(PostDTO postDTO, boolean unPublish) {
        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Object> article = new HashMap<>();
        article.put("title", postDTO.getTitle());
        article.put("published", true);
        article.put("body_markdown", postDTO.getContent());
        if (unPublish) {
            article.put("published", false);
        }
        List<String> tags = new ArrayList<>();
        tags.add(postDTO.getTag().replace(" ", ""));
        article.put("tags", tags);
        requestBody.put("article", article);
        return requestBody;
    }
}
