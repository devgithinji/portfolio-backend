package com.densoft.portfolio.service.project;

import com.densoft.portfolio.dto.ProjectCreateDTO;
import com.densoft.portfolio.dto.ProjectResponse;
import com.densoft.portfolio.dto.ProjectUpdateDTO;
import com.densoft.portfolio.exceptions.ResourceNotFoundException;
import com.densoft.portfolio.model.Project;
import com.densoft.portfolio.repository.ProjectRepository;
import com.densoft.portfolio.utils.AppConstants;
import com.densoft.portfolio.utils.CloudinaryUtil;
import com.densoft.portfolio.utils.Util;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;


    private final Util util;

    public ProjectServiceImpl(ProjectRepository projectRepository, Util util) {
        this.projectRepository = projectRepository;
        this.util = util;
    }

    @Override
    public ProjectResponse getProjects(String tag, Integer pageNo) {
        //create a pageable instance
        Sort sort = Sort.by(AppConstants.DEFAULT_SORT_BY).descending();
        Pageable pageable = PageRequest.of(pageNo, Integer.parseInt(AppConstants.DEFAULT_PAGE_SIZE), sort);
        Page<Project> projectPage;

        if (tag != null && !tag.equals("All")) {
            projectPage = projectRepository.findProjectByTags_Name(pageable, tag);
        } else {
            projectPage = projectRepository.findAll(pageable);
        }


        return new ProjectResponse(
                projectPage.getContent(),
                projectPage.getNumber(),
                projectPage.getSize(),
                projectPage.getTotalElements(),
                projectPage.getTotalPages(),
                projectPage.isLast()
        );
    }

    @Override
    public List<Project> getRandomProjects(Integer limit) {
        return projectRepository.findRandomProjects(limit);
    }


    @Override
    public Project getProject(Integer projectId) {
        return getExistingProject(projectId);
    }


    @Override
    public Project createProject(ProjectCreateDTO projectDTO) {
        String filePath = CloudinaryUtil.UploadFile(projectDTO.getImage(), "projects", true);
        Project project = new Project(projectDTO.getName(), projectDTO.getDescription(), projectDTO.getSiteLink(), projectDTO.getRepoLink(), filePath, true);
        //add tags
        project.addTags(util.generateTags(projectDTO.getTags()));
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(ProjectUpdateDTO projectUpdateDTO, Integer projectId) throws IOException {
        Project project = getExistingProject(projectId);
        if (projectUpdateDTO.getImage() != null) {
            if(project.getImage() != null){
                CloudinaryUtil.deleteFile(project.getImage());
            }
            String filePath = CloudinaryUtil.UploadFile(projectUpdateDTO.getImage(), "projects", true);
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
            CloudinaryUtil.deleteFile(project.getImage());
        }
        //clear tags
        project.clearTags();
        projectRepository.deleteById(projectId);
    }

    private Project getExistingProject(Integer projectId) {
        return projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("project", "Id", String.valueOf(projectId)));
    }
}
