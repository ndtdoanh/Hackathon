package com.hacof.submission.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileUrlResponseDTO {

    String fileName;
    String fileUrl;
    String fileType;
    int fileSize;

    public FileUrlResponseDTO(String fileName, String fileUrl, String fileType, int fileSize) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }
}
