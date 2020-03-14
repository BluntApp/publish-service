package com.blunt.publish.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "onboard-service")
public interface OnboardServiceProxyClient {
  @GetMapping(value = "/api/v1/onboard/blunt/{id}")
  public String getBluntName(@PathVariable String id) ;
}
