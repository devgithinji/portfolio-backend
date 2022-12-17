package com.densoft.portfolio.controller;

import com.densoft.portfolio.restClient.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RestService restService;

    @GetMapping
    public String getUser() {
        restService.getUserId();
        return "dennis";
    }
}
