package com.densoft.portfolio.service.project;

import com.densoft.portfolio.dto.ProjectCreateDTO;
import com.densoft.portfolio.dto.ProjectResponse;
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
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;


    private final Util util;

    public ProjectServiceImpl( ProjectRepository projectRepository, Util util) {
        this.projectRepository = projectRepository;
        this.util = util;
    }

    @Override
    public List<ProjectResponse> getProjects(String tag) {
        if (tag == null) return mapProjectResponse(projectRepository.findAll());

        return mapProjectResponse(projectRepository.findProjectByTags_Name(tag));
    }

    private List<ProjectResponse> mapProjectResponse(List<Project> projects) {
        return projects.stream().map(this::getProjectResponse).collect(Collectors.toList());
    }

    private ProjectResponse getProjectResponse(Project project) {
        ProjectResponse res = new ProjectResponse();
        res.setId(project.getId());
        res.setName(project.getName());
        res.setSiteLink(project.getSiteLink());
        res.setRepoLink(project.getRepoLink());
        res.setDescription(project.getDescription());
        res.setTags(project.getTags());
        res.setPublished(project.isPublished());
        res.setImage(AWSS3Util.getFileUrl(project.getImage()));
        return res;
    }

    @Override
    public ProjectResponse getProject(Integer projectId) {
        return getProjectResponse(getExistingProject(projectId));
    }


    @Override
    public Project createProject(ProjectCreateDTO projectDTO) throws IOException {
        String filePath = AWSS3Util.uploadFile("projects", projectDTO.getImage(), ObjectCannedACL.PUBLIC_READ);
        Project project = new Project(projectDTO.getName(), projectDTO.getDescription(), projectDTO.getSiteLink(), projectDTO.getRepoLink(), filePath, true);
        //add tags
        project.addTags(util.generateTags(projectDTO.getTags()));
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(ProjectUpdateDTO projectUpdateDTO, Integer projectId) throws IOException {
        Project project = getExistingProject(projectId);
        if (projectUpdateDTO.getImage() != null) {
            AWSS3Util.deleteFile(project.getImage());
            String filePath = AWSS3Util.uploadFile("projects", projectUpdateDTO.getImage(), ObjectCannedACL.PUBLIC_READ);
            project.setImage(filePath);
        }

        project.setName(projectUpdateDTO.getName());
        project.setDescription(projectUpdateDTO.getDescription());
        project.setSiteLink(projectUpdateDTO.getSiteLink());
        project.setRepoLink(projectUpdateDTO.getRepoLink());
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
            AWSS3Util.deleteFile(project.getImage());
        }
        //clear tags
        project.clearTags();
        projectRepository.deleteById(projectId);
    }

    private Project getExistingProject(Integer projectId) {
        return projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("project", "Id", String.valueOf(projectId)));
    }
}
