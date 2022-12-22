package com.densoft.portfolio.dto;

import com.densoft.portfolio.validators.fileType.FileType;
import com.densoft.portfolio.validators.fileType.ValidFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO extends UserBaseDTO {


    @ValidFile(fileType = FileType.DOC, message = "file type: pdf and Max Size: 3MB", maxSize = 3)
    private MultipartFile resume;

}
