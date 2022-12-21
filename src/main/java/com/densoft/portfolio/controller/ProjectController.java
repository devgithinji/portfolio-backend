package com.densoft.portfolio.controller;


import com.densoft.portfolio.dto.ProjectDTO;
import com.densoft.portfolio.model.Project;
import com.densoft.portfolio.service.project.ProjectService;
import com.densoft.portfolio.utils.AWSS3Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private AWSS3Util awss3Util;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("{tagId}")
    public List<Project> getProjectsByTag(@PathVariable("tagId") Integer tagId) {
        return projectService.getProjectsByTag(tagId);

    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Project createProject(@Valid ProjectDTO projectDTO) throws IOException {
        String filePath = awss3Util.uploadFile("projects", projectDTO.getImage(), ObjectCannedACL.PUBLIC_READ);

        return projectService.createProject(projectDTO, filePath);
    }
}
