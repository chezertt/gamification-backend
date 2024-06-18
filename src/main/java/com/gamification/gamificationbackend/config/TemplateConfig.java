package com.gamification.gamificationbackend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "template")
public class TemplateConfig {

    String invitationLetterTemplatePath;

    String playerRegistrationLink;
}
