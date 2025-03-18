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
}
