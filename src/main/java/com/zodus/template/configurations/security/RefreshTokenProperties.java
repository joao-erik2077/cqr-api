package com.zodus.template.configurations.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt.refresh-token")
public class RefreshTokenProperties {
  private String secret;
  private long expirationTimeInHours;
  private String issuer;
}
