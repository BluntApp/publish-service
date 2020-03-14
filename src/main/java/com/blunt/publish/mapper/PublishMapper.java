package com.blunt.publish.mapper;

import com.blunt.publish.dto.ContentDto;
import com.blunt.publish.dto.PostDto;
import com.blunt.publish.entity.Content;
import com.blunt.publish.entity.Post;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper
public interface PublishMapper {
  PostDto postToPostDto(Post post);

  List<PostDto> postListToPostDtoList(List<Post> post);

  List<Post> postDtoListToPostList(List<PostDto> postDto);

  Post postDtoToPost(PostDto postDto);

  ContentDto contentToContentDto(Content content);

  Content contentDtoToContent(ContentDto contentDto);

}
