package com.densoft.portfolio.service.project;

import com.densoft.portfolio.dto.ProjectDTO;
import com.densoft.portfolio.model.Project;
import com.densoft.portfolio.repository.PostRepository;
import com.densoft.portfolio.repository.ProjectRepository;
import com.densoft.portfolio.utils.Util;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Util util;
    @Autowired
    private PostRepository postRepository;


    @Override
    public Project createProject(ProjectDTO projectDTO, String filePath) {
        Project project = new Project(projectDTO.getName(), projectDTO.getDescription(), projectDTO.getSiteLink(), projectDTO.getRepoLink(), filePath, true);

        for (String tag : projectDTO.getTags()) {
            project.addTag(util.generateTag(tag));
            System.out.println(util.generateTag(tag).getName());
        }
        System.out.println("done");
        return projectRepository.save(project);
    }
}
