package com.blunt.publish.controller;

import com.blunt.publish.dto.PostDto;
import com.blunt.publish.service.PublishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/publish")
public class PublishController {

  private final PublishService publishService;

  @GetMapping("test")
  public ResponseEntity<String> testPublish(){
    return new ResponseEntity<>("success", HttpStatus.OK);
  }

  @PostMapping("post")
  public ResponseEntity<Object> publishPost(
      @RequestHeader(name = "BLUNT-ID", required = true) String bluntId,
      @RequestBody PostDto postDto) {
    return publishService.publishPost(postDto, bluntId);
  }

  @GetMapping("posts")
  public ResponseEntity<Object> getPosts(
      @RequestHeader(name = "BLUNT-ID", required = true) String bluntId) {
    return publishService.getPosts(bluntId);
  }

  @GetMapping("post/{postId}")
  public ResponseEntity<Object> getPost(
      @RequestHeader(name = "BLUNT-ID", required = true) String bluntId,
      @PathVariable String postId) {
    return publishService.getPost(bluntId, postId);
  }

  @GetMapping("content/{contentId}")
  public ResponseEntity<Object> getContent(
      @RequestHeader(name = "BLUNT-ID", required = true) String bluntId,
      @PathVariable String contentId) {
    return publishService.getContent(bluntId, contentId);
  }

  @GetMapping("metrics")
  public ResponseEntity<Object> getMetrics(
      @RequestHeader(name = "BLUNT-ID", required = true) String bluntId) {
    return publishService.getMetrics(bluntId);
  }
}
