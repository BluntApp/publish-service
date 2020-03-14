package com.blunt.publish.repository;

import com.blunt.publish.entity.Content;
import com.blunt.publish.entity.Post;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends MongoRepository<Content, ObjectId> {
  Content findAllById(ObjectId id);
}
