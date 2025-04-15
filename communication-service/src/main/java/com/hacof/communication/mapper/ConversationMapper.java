package com.hacof.communication.mapper;

import com.hacof.communication.dto.response.ConversationResponse;
import com.hacof.communication.entity.Conversation;
import com.hacof.communication.entity.FileUrl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ConversationMapper {

    @Mapping(target = "id", expression = "java(String.valueOf(conversation.getId()))")
    @Mapping(
            target = "createdByUserName",
            expression = "java(conversation.getCreatedBy() != null ? conversation.getCreatedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    ConversationResponse toConversationResponse(Conversation conversation);

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
