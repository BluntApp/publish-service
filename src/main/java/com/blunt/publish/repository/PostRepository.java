package com.blunt.publish.repository;

import com.blunt.publish.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, ObjectId> {

  List<Post> findAllByViewerId(ObjectId viewerId);
  Post findByIdAndViewerId(ObjectId id, ObjectId viewerId);
  Post findByViewerIdAndContentId(ObjectId viewerId, ObjectId contentId);
  Long countAllByPosterIdAndViewerId(ObjectId posterId, ObjectId viewerId);
  List<Post> findAllByPosterIdAndViewerIdAndPostedOnAfter(ObjectId posterId,ObjectId viewerId, LocalDateTime postedOn);
}
