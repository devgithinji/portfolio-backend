package com.densoft.portfolio.controller;

import com.densoft.portfolio.dto.ProfileResponse;
import com.densoft.portfolio.dto.UserCreateDTO;
import com.densoft.portfolio.dto.UserUpdateDTO;
import com.densoft.portfolio.restClient.RestService;
import com.densoft.portfolio.service.user.UserService;
import com.densoft.portfolio.utils.AWSS3Util;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/profile")
public class UserController {


    private final RestService restService;

    private final UserService userService;

    public UserController(RestService restService, UserService userService) {
        this.restService = restService;
        this.userService = userService;
    }

    @GetMapping
    public ProfileResponse getProfile() throws JsonProcessingException {
        return userService.getProfile();
    }

    @PostMapping
    public ProfileResponse createProfile(@Valid UserCreateDTO userCreateDTO) throws IOException {
        return userService.createProfile(userCreateDTO);
    }

    @PutMapping
    public ProfileResponse updateUser(@Valid UserUpdateDTO userUpdateDTO) throws IOException {
        return userService.updateUser(userUpdateDTO);
    }

    @GetMapping(value = "/download-resume")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam("fileName") String fileName) {
        final byte[] data = AWSS3Util.downloadFile(fileName);
        final ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok().contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

}
