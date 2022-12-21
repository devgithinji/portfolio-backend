package com.densoft.portfolio.service.image;

import com.densoft.portfolio.dto.ImageDTO;
import com.densoft.portfolio.model.Image;

import java.io.IOException;

public interface ImageService {

    Image saveImage(ImageDTO imageDTO) throws IOException;

    void deleteImage(Integer imageId);
}
