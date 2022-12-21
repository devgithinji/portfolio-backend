package com.densoft.portfolio.controller;

import com.densoft.portfolio.model.Image;
import com.densoft.portfolio.service.image.ImageService;
import com.densoft.portfolio.validators.fileType.FileType;
import com.densoft.portfolio.validators.fileType.ValidFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping
    public Image addImage(@ValidFile(fileType = FileType.IMAGE, message = "image is required") @RequestParam("image") MultipartFile multipartFile) {

        return null;
    }



}
