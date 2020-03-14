package com.blunt.publish.config;

import brave.sampler.Sampler;
import com.blunt.publish.mapper.PublishMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PublishServiceConfiguration {

  @Bean
  public Sampler defaultSampler() {
    return Sampler.ALWAYS_SAMPLE;
  }

  @Bean
  public PublishMapper bluntMapper(){
    PublishMapper mapper = Mappers.getMapper(PublishMapper.class);
    return mapper;
  }

}
