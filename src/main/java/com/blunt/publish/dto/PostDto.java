package com.blunt.publish.dto;

import com.blunt.publish.entity.Content;
import com.blunt.publish.serializer.ObjectIdSerializer;
import com.blunt.publish.type.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.LastModifiedDate;

@Data
public class PostDto {

  @JsonSerialize(using = ObjectIdSerializer.class)
  private ObjectId id;
  @JsonSerialize(using = ObjectIdSerializer.class)
  private ObjectId posterId;
  @JsonSerialize(using = ObjectIdSerializer.class)
  private ObjectId viewerId;
  @JsonSerialize(using = ObjectIdSerializer.class)
  private ObjectId contentId;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime postedOn;
  private String posterName;
  private boolean isCommentPublic;
  private List<ObjectId> viewerList;
  private ContentDto contentDto;
  private List<CommentsDto> comments;

}
