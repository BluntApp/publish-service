package com.blunt.publish.entity;

import com.blunt.publish.serializer.ObjectIdSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Post {

  @JsonSerialize(using = ObjectIdSerializer.class)
  private ObjectId id;
  @JsonSerialize(using = ObjectIdSerializer.class)
  private ObjectId posterId;
  @JsonSerialize(using = ObjectIdSerializer.class)
  private String posterName;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  @LastModifiedDate
  private LocalDateTime postedOn;
  private ObjectId viewerId;
  @JsonSerialize(using = ObjectIdSerializer.class)
  private ObjectId contentId;
  private boolean isCommentPublic;


}
