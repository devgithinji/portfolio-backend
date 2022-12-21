package com.densoft.portfolio.service.project;

import com.densoft.portfolio.dto.ProjectCreateDTO;
import com.densoft.portfolio.dto.ProjectUpdateDTO;
import com.densoft.portfolio.exceptions.ResourceNotFoundException;
import com.densoft.portfolio.model.Project;
import com.densoft.portfolio.repository.ProjectRepository;
import com.densoft.portfolio.utils.AWSS3Util;
import com.densoft.portfolio.utils.Util;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;

import java.io.IOException;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final AWSS3Util awss3Util;
    private final ProjectRepository projectRepository;


    private final Util util;

    public ProjectServiceImpl(AWSS3Util awss3Util, ProjectRepository projectRepository, Util util) {
        this.awss3Util = awss3Util;
        this.projectRepository = projectRepository;
        this.util = util;
    }

    @Override
    public List<Project> getProjects(String tag) {
        if (tag == null) return projectRepository.findAll();
        return projectRepository.findProjectByTags_Name(tag);
    }

    @Override
    public Project getProject(Integer projectId) {
        return getExistingProject(projectId);
    }


    @Override
    public Project createProject(ProjectCreateDTO projectDTO) throws IOException {
        String filePath = awss3Util.uploadFile("projects", projectDTO.getImage(), ObjectCannedACL.PUBLIC_READ);
        Project project = new Project(projectDTO.getName(), projectDTO.getDescription(), projectDTO.getSiteLink(), projectDTO.getRepoLink(), filePath, true);
        //add tags
        project.addTags(util.generateTags(projectDTO.getTags()));
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(ProjectUpdateDTO projectUpdateDTO, Integer projectId) throws IOException {
        Project project = getExistingProject(projectId);
        if (projectUpdateDTO.getImage() != null) {
            awss3Util.deleteFile(project.getImage());
            String filePath = awss3Util.uploadFile("projects", projectUpdateDTO.getImage(), ObjectCannedACL.PUBLIC_READ);
            project.setImage(filePath);
        }

        project.setName(projectUpdateDTO.getName());
        project.setDescription(projectUpdateDTO.getDescription());
        project.setSiteLink(projectUpdateDTO.getSiteLink());
        project.setRepoLink(project.getRepoLink());
        //clear tags
        project.clearTags();
        //add tags
        project.addTags(util.generateTags(projectUpdateDTO.getTags()));

        return projectRepository.save(project);
    }

    @Override
    public Project togglePublishStatus(Integer projectId) {
        Project project = getExistingProject(projectId);
        project.setPublished(!project.isPublished());
        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(Integer projectId) {
        Project project = getExistingProject(projectId);
        if (project.getImage() != null) {
            awss3Util.deleteFile(project.getImage());
        }
        //clear tags
        project.clearTags();
        projectRepository.deleteById(projectId);
    }

    private Project getExistingProject(Integer projectId) {
        return projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("project", "Id", String.valueOf(projectId)));
    }
}
