package com.densoft.portfolio.restClient;

import lombok.Data;


@Data
public class PostResponse {
    public Post data;

    @Data
    public class Post {
        private String id;
        private String title;
        private String authorId;
        private String url;
        private String publishStatus;
        private String publishedAt;
        private String license;
        private String licenseUrl;
        private String[] tags;
    }
}
