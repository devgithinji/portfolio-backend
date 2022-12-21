package com.densoft.portfolio.controller;

import com.densoft.portfolio.dto.UserCreateDTO;
import com.densoft.portfolio.model.User;
import com.densoft.portfolio.restClient.RestService;
import com.densoft.portfolio.service.user.UserService;
import com.densoft.portfolio.utils.AWSS3Util;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {

    private final AWSS3Util awss3Util;

    private final RestService restService;

    private final UserService userService;

    public UserController(AWSS3Util awss3Util, RestService restService, UserService userService) {
        this.awss3Util = awss3Util;
        this.restService = restService;
        this.userService = userService;
    }

    @GetMapping
    public String getUser() {
        restService.getUserId();
        return "dennis";
    }

    @PostMapping
    public User createProfile(@Valid UserCreateDTO userCreateDTO) throws IOException {

        return userService.createProfile(userCreateDTO);
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
