package com.hacof.submission.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileUrlResponseDTO {

    private String fileName;
    private String fileUrl;
    private String fileType;
    private int fileSize;

    public FileUrlResponseDTO(String fileName, String fileUrl, String fileType, int fileSize) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }
}
