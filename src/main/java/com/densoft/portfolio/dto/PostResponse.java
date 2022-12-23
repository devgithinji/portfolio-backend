package com.densoft.portfolio.dto;

import com.densoft.portfolio.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class PostResponse {
    private List<Post> posts;

    private int pagNo;

    private int pageSize;

    private long totalElements;

    private int totalPages;

    private boolean last;
}
