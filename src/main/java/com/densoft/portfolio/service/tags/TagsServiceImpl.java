package com.densoft.portfolio.service.tags;

import com.densoft.portfolio.model.Tag;
import com.densoft.portfolio.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagsServiceImpl implements TagsService {

    private final TagRepository tagRepository;

    public TagsServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> getTags() {
        return tagRepository.findAll();
    }
}
