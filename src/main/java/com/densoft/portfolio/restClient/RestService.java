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


    public String createPost(PostDTO postDTO) {
        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Object> article = new HashMap<>();
        article.put("title", postDTO.getTitle());
        article.put("published", true);
        article.put("body_markdown", postDTO.getContent());
        List<String> tags = new ArrayList<>();
        tags.add(postDTO.getTag().replace(" ", ""));
        article.put("tags", tags);
        System.out.println(article);
        requestBody.put("article", article);
        Map response = webClient.post().uri("/articles").bodyValue(requestBody).retrieve().bodyToMono(Map.class).block();
        return response.get("url").toString();
    }
}
