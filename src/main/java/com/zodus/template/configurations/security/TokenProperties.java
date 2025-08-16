package com.zodus.template.configurations.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt.token")
public class TokenProperties {
  private String secret;
  private long expirationTimeInDays;
  private String issuer;
}
