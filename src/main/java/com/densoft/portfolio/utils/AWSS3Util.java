package com.densoft.portfolio.utils;

import com.densoft.portfolio.exceptions.ApIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import javax.activation.MimetypesFileTypeMap;
import java.io.IOException;
import java.io.InputStream;


@Component
public class AWSS3Util {

    @Autowired
    private S3Client s3Client;

    @Value("${AWS_BUCKET_NAME}")
    public String BUCKET_NAME;


    public void uploadFile(String folderName, String fileName, MultipartFile multipartFile, ObjectCannedACL objectCannedACL) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();
        PutObjectRequest request = PutObjectRequest.builder().bucket(BUCKET_NAME)
                .key(folderName + "/" + fileName).acl(objectCannedACL).contentType(multipartFile.getContentType()).build();

        try {
            int contentLength = inputStream.available();
            s3Client.putObject(request, RequestBody.fromInputStream(inputStream, contentLength));
        } catch (IOException e) {
            throw new ApIException("could not upload file");
        }
    }

    public byte[] downloadFile(String fileName) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(BUCKET_NAME).key(fileName).build();
            ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(getObjectRequest);
            return objectBytes.asByteArray();
        } catch (Exception e) {
            throw new ApIException("Could not download file");
        }
    }

    public void deleteFile(String fileName) {
        try {
            DeleteObjectRequest request = DeleteObjectRequest.builder().bucket(BUCKET_NAME).key(fileName).build();
            s3Client.deleteObject(request);
        } catch (Exception e) {
            throw new ApIException("Could not delete file");
        }
    }


}
