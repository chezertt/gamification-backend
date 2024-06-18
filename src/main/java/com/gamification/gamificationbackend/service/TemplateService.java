package com.gamification.gamificationbackend.service;

import java.util.Map;

public interface TemplateService {

    String processTemplate(String template, Map<String, Object> variables);
}
