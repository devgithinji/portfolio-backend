package com.densoft.portfolio.service.tags;

import com.densoft.portfolio.exceptions.ApIException;
import com.densoft.portfolio.exceptions.ResourceNotFoundException;
import com.densoft.portfolio.model.Post;
import com.densoft.portfolio.model.Project;
import com.densoft.portfolio.model.Tag;
import com.densoft.portfolio.repository.PostRepository;
import com.densoft.portfolio.repository.ProjectRepository;
import com.densoft.portfolio.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TagsServiceImpl implements TagsService {

    private final TagRepository tagRepository;

    private final PostRepository postRepository;

    private final ProjectRepository projectRepository;

    public TagsServiceImpl(TagRepository tagRepository, PostRepository postRepository, ProjectRepository projectRepository) {
        this.tagRepository = tagRepository;
        this.postRepository = postRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Tag> getTags() {
        return tagRepository.findAll();
    }

    @Override
    public Tag createTag(String tag) {
        Optional<Tag> existingTag = tagRepository.findByName(tag);
        if (existingTag.isPresent()) throw new ApIException("category name is taken");
        return tagRepository.save(new Tag(tag));
    }

    @Override
    public Tag updateTag(String tagName, Integer tagId) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("category", "Id", String.valueOf(tagId)));
        Optional<Tag> tagWithTitle = tagRepository.findTagWithMatchingTitle(tagName, tagId);
        if (tagWithTitle.isPresent()) throw new ApIException("category name is taken");
        tag.setName(tagName);
        return tagRepository.save(tag);
    }


    @Override
    public void deleteTag(Integer tagId) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("category", "Id", String.valueOf(tagId)));
        Set<Post> posts = tag.getPosts();
        posts.forEach(post -> {
            post.setTag(null);
            postRepository.save(post);
        });

        Set<Project> projects = tag.getProjects();
        projects.forEach(project -> {
            project.getTags().remove(tag);
            projectRepository.save(project);
        });

        tagRepository.deleteById(tagId);
    }
}
