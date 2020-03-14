package com.blunt.publish.dto;

import com.blunt.publish.serializer.ObjectIdSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class PostMetricsDto {
  @JsonSerialize(using = ObjectIdSerializer.class)
  private ObjectId bluntId;
  private Long totalPost;
  private ArrayList<ArrayList<String>> monthlyPost;
}
