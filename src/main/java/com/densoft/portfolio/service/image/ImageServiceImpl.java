package com.densoft.portfolio.service.image;

import com.densoft.portfolio.dto.ImageDTO;
import com.densoft.portfolio.exceptions.ResourceNotFoundException;
import com.densoft.portfolio.model.Image;
import com.densoft.portfolio.model.Post;
import com.densoft.portfolio.repository.ImageRepository;
import com.densoft.portfolio.repository.PostRepository;
import com.densoft.portfolio.utils.AWSS3Util;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    private final PostRepository postRepository;


    public ImageServiceImpl(ImageRepository imageRepository, PostRepository postRepository) {
        this.imageRepository = imageRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Image saveImage(ImageDTO imageDTO) throws IOException {
        Post post = postRepository.findById(Integer.parseInt(imageDTO.getPostId())).orElseThrow(() -> new ResourceNotFoundException("post", "Id", String.valueOf(imageDTO.getPostId())));
        String filePath = AWSS3Util.uploadFile("posts", imageDTO.getImage(), ObjectCannedACL.PUBLIC_READ);
        Image image = new Image(filePath, post);
        return imageRepository.save(image);
    }

    @Override
    public void deleteImage(Integer imageId) {
        Image image = imageRepository.findById(imageId).orElseThrow(() -> new ResourceNotFoundException("image", "Id", String.valueOf(imageId)));
        AWSS3Util.deleteFile(image.getPath());
        imageRepository.deleteById(imageId);
    }
}
