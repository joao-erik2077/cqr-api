package com.zodus.template.configurations.cors;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@AllArgsConstructor
public class CorsConfiguration implements WebMvcConfigurer {
  private final CorsProperties corsProperties;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins(corsProperties.getAllowedOrigins())
        .allowedMethods(corsProperties.getAllowedMethods())
        .allowedHeaders(corsProperties.getAllowedHeaders());
  }
}
