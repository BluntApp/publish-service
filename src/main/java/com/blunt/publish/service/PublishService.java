package com.blunt.publish.service;

import com.blunt.publish.dto.ContentDto;
import com.blunt.publish.dto.PostDto;
import com.blunt.publish.dto.PostMetricsDto;
import com.blunt.publish.entity.Content;
import com.blunt.publish.entity.Post;
import com.blunt.publish.error.BluntException;
import com.blunt.publish.mapper.PublishMapper;
import com.blunt.publish.proxy.OnboardServiceProxyClient;
import com.blunt.publish.repository.ContentRepository;
import com.blunt.publish.repository.PostRepository;
import com.blunt.publish.util.BluntConstant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublishService {

  private final PostRepository postRepository;
  private final ContentRepository contentRepository;
  private final PublishMapper publishMapper;
  private final OnboardServiceProxyClient onboardServiceProxyClient;

  public ResponseEntity<Object> publishPost(PostDto postDto, String bluntId) {
    String bluntName = onboardServiceProxyClient.getBluntName(bluntId);
    Content content = publishMapper.contentDtoToContent(postDto.getContentDto());
    content.setPosterId(new ObjectId(bluntId));
    contentRepository.save(content);
    postDto.setPosterId(new ObjectId(bluntId));
    postDto.setContentId(content.getId());
    postDto.setViewerId(new ObjectId(bluntId));
    postDto.setPosterName(bluntName);
    postDto.setPostedOn(LocalDateTime.now());
    Post post = publishMapper.postDtoToPost(postDto);
    postRepository.save(post);
    postDto.getViewerList().parallelStream().forEach(viewerId ->{
      Post viewerPost = publishMapper.postDtoToPost(postDto);
      viewerPost.setViewerId(viewerId);
      postRepository.save(viewerPost);
    });
    return new ResponseEntity<>(publishMapper.postToPostDto(post), HttpStatus.OK);
  }

  public ResponseEntity<Object> getPosts(String bluntId) {
    List<Post> postList = postRepository.findAllByViewerId(new ObjectId(bluntId));
    postList.sort(new Comparator<Post>() {
      @Override
      public int compare(Post o1, Post o2) {
        return o2.getPostedOn().compareTo(o1.getPostedOn());
      }
    });
    List<PostDto> postDtoList = publishMapper.postListToPostDtoList(postList);
    return new ResponseEntity(postDtoList, HttpStatus.OK);
  }

  public ResponseEntity<Object> getPost(String bluntId, String postId) {
    Post post = postRepository.findByIdAndViewerId(new ObjectId(postId), new ObjectId(bluntId));
    return new ResponseEntity(publishMapper.postToPostDto(post), HttpStatus.OK);
  }

  public ResponseEntity<Object> getContent(String bluntId, String contentId){
    if(Objects.isNull(postRepository.findByViewerIdAndContentId(new ObjectId(bluntId), new ObjectId(contentId)))){
      throw new BluntException(BluntConstant.CONTENT_NOT_FOUND, HttpStatus.NO_CONTENT.value(),
          HttpStatus.NO_CONTENT);
    }
    Content content = contentRepository.findAllById(new ObjectId(contentId));
    ContentDto contentDto = publishMapper.contentToContentDto(content);
    return new ResponseEntity<>(contentDto,HttpStatus.OK);
  }

  public ResponseEntity<Object> getMetrics(String bluntId) {
    PostMetricsDto postMetricsDto = new PostMetricsDto();
    postMetricsDto.setBluntId(new ObjectId(bluntId));
    Long postedCount = postRepository.countAllByPosterIdAndViewerId(postMetricsDto.getBluntId(), postMetricsDto.getBluntId());
    mapPostDetails(postMetricsDto);
    postMetricsDto.setTotalPost(postedCount);
    return new ResponseEntity<>(postMetricsDto, HttpStatus.OK);
  }

  private void mapPostDetails(PostMetricsDto postMetricsDto) {
    TemporalAdjuster temporalAdjuster = TemporalAdjusters.lastDayOfMonth();
    LocalDateTime lastOneYear = LocalDateTime.now().with(temporalAdjuster).minusYears(1);
    List<Post> postList = postRepository.findAllByPosterIdAndViewerIdAndPostedOnAfter(postMetricsDto.getBluntId(), postMetricsDto.getBluntId(),lastOneYear);
    Map<Month, Long> monthCountMap = postList
        .parallelStream()
        .collect(Collectors.groupingBy(date -> date.getPostedOn().getMonth(), Collectors.counting()));

    ArrayList<Month> lastTwelveMonthsList = getLastTwelveMonthsList();
    ArrayList<ArrayList<String>> listOfDataMap = new ArrayList<>();
    lastTwelveMonthsList.forEach(month -> {
      ArrayList<String> dataMap = new ArrayList<>();
      dataMap.add(month.toString());
      dataMap.add(monthCountMap.get(month)!=null?monthCountMap.get(month).toString():"0");
      listOfDataMap.add(dataMap);
    });

    postMetricsDto.setMonthlyPost(listOfDataMap);
  }

  private ArrayList<Month> getLastTwelveMonthsList() {
    TemporalAdjuster temporalAdjuster = TemporalAdjusters.lastDayOfMonth();
    ArrayList<Month> lastTwelveMonths = new ArrayList<>();
    for(int i=11;i>=0;i--){
      LocalDateTime calculatedDateTime = LocalDateTime.now().with(temporalAdjuster).minusMonths(i);
      lastTwelveMonths.add(calculatedDateTime.getMonth());
    }
    return lastTwelveMonths;
  }


}
