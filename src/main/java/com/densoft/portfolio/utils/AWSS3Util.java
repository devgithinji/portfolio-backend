package com.densoft.portfolio.utils;

import com.densoft.portfolio.exceptions.ApIException;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.InputStream;

import static com.densoft.portfolio.utils.Util.generateRandomUUID;


public class AWSS3Util {


    public static String BUCKET_NAME;


    static {
        BUCKET_NAME = System.getenv("BUCKET_NAME");
    }


    public static String uploadFile(String folderName, MultipartFile multipartFile, ObjectCannedACL objectCannedACL) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();
        String fileName = generateRandomUUID() + StringUtils.cleanPath(multipartFile.getOriginalFilename()).replaceAll("\\s", "");
        String key = folderName + "/" + fileName;
        PutObjectRequest request = PutObjectRequest.builder().bucket(BUCKET_NAME)
                .key(key).acl(objectCannedACL).contentType(multipartFile.getContentType()).build();

        try {
            int contentLength = inputStream.available();
            s3Client().putObject(request, RequestBody.fromInputStream(inputStream, contentLength));
            return key;
        } catch (IOException e) {
            throw new ApIException("could not upload file");
        }
    }

    public static byte[] downloadFile(String fileName) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(BUCKET_NAME).key(fileName).build();
            ResponseBytes<GetObjectResponse> objectBytes = s3Client().getObjectAsBytes(getObjectRequest);
            return objectBytes.asByteArray();
        } catch (Exception e) {
            throw new ApIException("Could not download file");
        }
    }

    public static void deleteFile(String fileName) {
        try {
            DeleteObjectRequest request = DeleteObjectRequest.builder().bucket(BUCKET_NAME).key(fileName).build();
            s3Client().deleteObject(request);
        } catch (Exception e) {
            throw new ApIException("Could not delete file");
        }
    }

    public static String getFileUrl(String objectKey) {
        GetUrlRequest request = GetUrlRequest.builder().bucket(BUCKET_NAME).key(objectKey).build();
        return s3Client().utilities().getUrl(request).toString();
    }

    private static S3Client s3Client() {
        return S3Client.builder().build();
    }


}
