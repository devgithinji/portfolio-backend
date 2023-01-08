package com.densoft.portfolio.service.tags;

import com.densoft.portfolio.model.Tag;

import java.util.List;

public interface TagsService {

    List<Tag> getTags();

    Tag createTag(String tagName);

    Tag updateTag(String tagName, Integer tagId);

    void deleteTag(Integer tagId);
}
