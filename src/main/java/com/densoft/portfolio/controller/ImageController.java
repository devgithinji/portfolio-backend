package com.densoft.portfolio.controller;

import com.densoft.portfolio.dto.ImageDTO;
import com.densoft.portfolio.model.Image;
import com.densoft.portfolio.service.image.ImageService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public Image addImage(@Valid ImageDTO imageDTO) throws IOException {

        return imageService.saveImage(imageDTO);
    }

    @DeleteMapping("{imageId}")
    public String deleteImage(@PathVariable("imageId") Integer imageId) {
        imageService.deleteImage(imageId);
        return "image deleted successfully";
    }


}
