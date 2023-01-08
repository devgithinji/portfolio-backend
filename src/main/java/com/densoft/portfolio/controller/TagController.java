package com.densoft.portfolio.controller;

import com.densoft.portfolio.dto.TagDTO;
import com.densoft.portfolio.model.Tag;
import com.densoft.portfolio.service.tags.TagsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
public class TagController {

    private final TagsService tagsService;

    public TagController(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    @GetMapping
    public List<Tag> getTags() {
        return tagsService.getTags();
    }

    @PostMapping
    public Tag addTag(@Valid @RequestBody TagDTO tagDTO) {
        System.out.println(tagDTO.getName());
        return tagsService.createTag(tagDTO.getName());
    }

    @PutMapping("{tagId}")
    public Tag updateTag(@PathVariable("tagId") Integer tagId, @Valid @RequestBody TagDTO tagDTO) {

        return tagsService.updateTag(tagDTO.getName(), tagId);
    }

    @DeleteMapping("{tagId}")
    public String deleteTag(@PathVariable("tagId") Integer tagId) {
        tagsService.deleteTag(tagId);
        return "Category deleted successfully";
    }
}
