package com.densoft.portfolio.controller;


import com.densoft.portfolio.dto.ProjectDTO;
import com.densoft.portfolio.model.Project;
import com.densoft.portfolio.service.project.ProjectService;
import com.densoft.portfolio.utils.AWSS3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;

import javax.validation.Valid;
import java.io.IOException;


@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private AWSS3Util awss3Util;

    @Autowired
    private ProjectService projectService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Project createProject(@RequestPart(value = "project") @Valid ProjectDTO projectDTO, @RequestPart(value = "image") MultipartFile multipartFile) throws IOException {
        String filePath = awss3Util.uploadFile("projects", multipartFile, ObjectCannedACL.PUBLIC_READ);
        return projectService.createProject(projectDTO, filePath);
    }
}
