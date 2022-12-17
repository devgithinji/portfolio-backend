package com.densoft.portfolio.restClient;

import lombok.Data;

import java.util.Map;

@Data
public class UserResponse {

    User data;

    @Data
    public class User {
        private String id;
        private String username;
        private String name;
        private String url;
        private String imageUrl;
    }
}


