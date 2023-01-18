package com.densoft.portfolio.controller;

import com.densoft.portfolio.utils.CloudinaryUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/cloudinary")
public class CloudinaryController {

    @PostMapping
    public String postImage(@RequestParam("file") MultipartFile multipartFile) {
        CloudinaryUtil.UploadFile(multipartFile, true);
        return "image posted";
    }

    @GetMapping
    public String deleteImage() {
        CloudinaryUtil.deleteFile("https://res.cloudinary.com/densoft-developers/image/upload/v1674045073/file_kmip6s.pdf");
        return "deleted";
    }
}
