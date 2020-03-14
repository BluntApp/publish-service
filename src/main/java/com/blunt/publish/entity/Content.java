package com.blunt.publish.entity;

import com.blunt.publish.serializer.ObjectIdSerializer;
import com.blunt.publish.type.ContentType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Content {
  @JsonSerialize(using = ObjectIdSerializer.class)
  private ObjectId id;
  @JsonSerialize(using = ObjectIdSerializer.class)
  private ObjectId posterId;
  private ContentType contentType;
  private String content;
}
