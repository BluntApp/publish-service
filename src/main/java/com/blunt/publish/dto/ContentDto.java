package com.blunt.publish.dto;

import com.blunt.publish.serializer.ObjectIdSerializer;
import com.blunt.publish.type.ContentType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
public class ContentDto {
  @JsonSerialize(using = ObjectIdSerializer.class)
  private ObjectId id;
  @JsonSerialize(using = ObjectIdSerializer.class)
  private ObjectId posterId;
  private ContentType contentType;
  private String content;
}
