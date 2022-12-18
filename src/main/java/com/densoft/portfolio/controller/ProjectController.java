package com.densoft.portfolio.controller;


import com.densoft.portfolio.model.Project;
import com.densoft.portfolio.utils.AWSS3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;

import java.io.IOException;

import static com.densoft.portfolio.utils.Util.generateRandomUUID;


@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private AWSS3Util awss3Util;

    @PostMapping
    public Project createProject(@RequestParam("image") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String fileName = generateRandomUUID() + StringUtils.cleanPath(file.getOriginalFilename()).replaceAll("\\s", "");
            String uploadDir = "projects";
            awss3Util.uploadFile(uploadDir, fileName, file, ObjectCannedACL.PUBLIC_READ);
        }
        return null;
    }
}
