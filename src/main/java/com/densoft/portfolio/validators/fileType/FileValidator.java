package com.densoft.portfolio.validators.fileType;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    FileType fileType;

    Long maxSize;

    boolean isRequired;

    @Override
    public void initialize(ValidFile constraintAnnotation) {
        fileType = constraintAnnotation.fileType();
        maxSize = (long) constraintAnnotation.maxSize() * 1024 * 1024;
        isRequired = constraintAnnotation.isRequired();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {

        if ((multipartFile == null || multipartFile.isEmpty()) && !isRequired) return true;

        if (multipartFile == null || multipartFile.isEmpty()) return false;

        if (multipartFile.getSize() > maxSize) return false;

        if (fileType.equals(FileType.IMAGE)) {
            return isImage(multipartFile.getContentType());
        }
        if (fileType.equals(FileType.DOC)) {
            return isPdf(multipartFile.getContentType());
        }

        return false;
    }

    public boolean isImage(String contentType) {
        return contentType.equals("image/jpeg") ||
                contentType.equals("image/png") ||
                contentType.equals("image/jpg");
    }

    public boolean isPdf(String contentType) {
        return contentType.equals("application/pdf");
    }
}
