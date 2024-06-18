package com.gamification.gamificationbackend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    Long accessTokenLifetimeInMs;
    String accessTokenSecret;

    Long refreshTokenLifetimeInMs;
    String refreshTokenSecret;

    String invitationTokenSecret;
}
