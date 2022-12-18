package com.densoft.portfolio.controller;

import com.densoft.portfolio.model.Project;
import com.densoft.portfolio.restClient.RestService;
import com.densoft.portfolio.utils.AWSS3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;

import java.io.IOException;

import static com.densoft.portfolio.utils.Util.generateRandomUUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AWSS3Util awss3Util;

    @Autowired
    private RestService restService;

    @GetMapping
    public String getUser() {
        restService.getUserId();
        return "dennis";
    }

    @PostMapping
    public Project createProfile(@RequestParam("image") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {;
            awss3Util.uploadFile( "profile", file, ObjectCannedACL.PRIVATE);
        }
        return null;
    }

    @GetMapping(value = "/download-resume")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam("fileName") String fileName) {
        final byte[] data = awss3Util.downloadFile(fileName);
        final ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok().contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

}
