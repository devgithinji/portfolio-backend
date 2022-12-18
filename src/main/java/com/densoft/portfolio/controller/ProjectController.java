package com.densoft.portfolio.controller;


import com.densoft.portfolio.dto.ProjectDTO;
import com.densoft.portfolio.model.Project;
import com.densoft.portfolio.utils.AWSS3Util;
import com.densoft.portfolio.validators.fileType.ValidFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;

import javax.validation.Valid;
import java.io.IOException;

import static com.densoft.portfolio.utils.Util.generateRandomUUID;


@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private AWSS3Util awss3Util;

    @PostMapping
    public Project createProject(@Valid @RequestBody ProjectDTO projectDTO) throws IOException {
        MultipartFile file = projectDTO.getImage();
        if (!file.isEmpty()) {
            awss3Util.uploadFile("projects",  file, ObjectCannedACL.PUBLIC_READ);
        }
        return null;
    }
}
