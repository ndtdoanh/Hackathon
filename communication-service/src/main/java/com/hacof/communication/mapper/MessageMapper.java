package com.hacof.communication.mapper;

import com.hacof.communication.dto.request.MessageRequest;
import com.hacof.communication.dto.response.MessageResponse;
import com.hacof.communication.entity.FileUrl;
import com.hacof.communication.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(target = "content", source = "content")
    @Mapping(target = "fileUrls", source = "fileUrls")
    Message toMessage(MessageRequest request);

    @Mapping(target = "id", expression = "java(String.valueOf(message.getId()))")
    @Mapping(target = "conversationId", source = "conversation.id")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "fileUrls", source = "fileUrls")
    @Mapping(
            target = "createdByUserName",
            expression = "java(message.getCreatedBy() != null ? message.getCreatedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    MessageResponse toMessageResponse(Message message);

    default List<FileUrl> map(List<String> fileUrls) {
        if (fileUrls == null) {
            return Collections.emptyList();
        }
        return fileUrls.stream()
                .map(fileUrlString -> {
                    FileUrl fileUrl = new FileUrl();
                    fileUrl.setFileUrl(fileUrlString);
                    return fileUrl;
                })
                .collect(Collectors.toList());
    }

    default List<String> mapFileUrlsToStrings(List<FileUrl> fileUrls) {
        if (fileUrls == null) {
            return Collections.emptyList();
        }
        return fileUrls.stream().map(FileUrl::getFileUrl).collect(Collectors.toList());
    }
}
