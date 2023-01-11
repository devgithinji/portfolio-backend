package com.densoft.portfolio.controller;


import com.densoft.portfolio.dto.ProjectCreateDTO;
import com.densoft.portfolio.dto.ProjectResponse;
import com.densoft.portfolio.dto.ProjectUpdateDTO;
import com.densoft.portfolio.model.Project;
import com.densoft.portfolio.service.project.ProjectService;
import com.densoft.portfolio.utils.AppConstants;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @GetMapping
    public ProjectResponse getAllProjects(@RequestParam(value = "category", defaultValue = AppConstants.DEFAULT_CATEGORY, required = false) String category,
                                          @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNo) {
        return projectService.getProjects(category, pageNo);
    }

    @GetMapping("random")
    public List<Project> getRandomProjects(@RequestParam(value = "limit", defaultValue = "3", required = false) int limit) {
        return projectService.getRandomProjects(limit);
    }

    @GetMapping("{projectId}")
    public Project getProject(@PathVariable("projectId") Integer projectId) {
        return projectService.getProject(projectId);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Project createProject(@Valid ProjectCreateDTO projectDTO) throws IOException {
        return projectService.createProject(projectDTO);
    }

    @PostMapping("update-status/{projectId}")
    public Project togglePublishStatus(@PathVariable("projectId") Integer projectId) {
        return projectService.togglePublishStatus(projectId);
    }

    @PutMapping("{projectId}")
    public Project updateProject(@PathVariable("projectId") Integer projectId, @Valid ProjectUpdateDTO projectDTO) throws IOException {
        return projectService.updateProject(projectDTO, projectId);
    }

    @DeleteMapping("{projectId}")
    public String deleteProject(@PathVariable("projectId") Integer projectId) {
        projectService.deleteProject(projectId);
        return "project deleted successfully";
    }
}
