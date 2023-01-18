package com.densoft.portfolio.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.densoft.portfolio.exceptions.ApIException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CloudinaryUtil {


    public static String cloud_name;
    public static String api_key;
    public static String api_secret;


    static {
        cloud_name = System.getenv("CLOUDINARY_CLOUD_NAME");
        api_key = System.getenv("CLOUDINARY_API_KEY");
        api_secret = System.getenv("CLOUDINARY_API_SECRET");
    }


    public static String UploadFile(MultipartFile multipartFile, boolean isImage) {
        return fileUpload(multipartFile, null, isImage);
    }

    public static String UploadFile(MultipartFile multipartFile, String folderName, boolean isImage) {
        return fileUpload(multipartFile, folderName, isImage);
    }


    private static String fileUpload(MultipartFile multipartFile, String folderName, boolean isImage) {
        try {

            Map<String, Object> options = new HashMap<>();
            if (folderName != null) {
                options.put("folder", "portfolio/" + folderName);
            } else {
                options.put("folder", "portfolio");
            }
            if (!isImage) {
                options.put("resource_type", "raw");
                options.put("unique_filename", false);
                options.put("public_id", multipartFile.getOriginalFilename());
            }
            options.put("use_filename", true);
            options.put("overwrite", true);

            Map result = getCloudinary().uploader().upload(multipartFile.getBytes(), options);
            return result.get("secure_url").toString();
        } catch (Exception e) {
            throw new ApIException("image upload error");
        }
    }

    public static void deleteFile(String path) {
        String[] data = path.split("/");
        String fileName = data[data.length - 1];
        String publicId = data[data.length - 3] + "/" + data[data.length - 2] + "/" + fileName.split("\\.")[0];

        fileDestroy(publicId, true);
    }

    public static void deleteFile(String path, boolean isImage) {
        String[] data = path.split("/");

        String publicId = data[data.length - 3] + "/" + data[data.length - 2] + "/" + data[data.length - 1];

        fileDestroy(publicId, isImage);
    }

    private static void fileDestroy(String publicId, boolean isImage) {
        try {
            if (isImage) {
                getCloudinary().uploader().destroy(publicId, ObjectUtils.emptyMap());
            } else {
                getCloudinary().uploader().destroy(publicId, ObjectUtils.asMap("resource_type", "raw"));
            }
        } catch (IOException e) {
            throw new ApIException("image deletion error");
        }
    }


    public static Cloudinary getCloudinary() {
        return new Cloudinary(ObjectUtils.asMap("cloud_name", cloud_name, "api_key", api_key, "api_secret", api_secret, "secure", true));
    }
}
