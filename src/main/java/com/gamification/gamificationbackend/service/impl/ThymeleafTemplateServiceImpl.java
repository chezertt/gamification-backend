package com.gamification.gamificationbackend.service.impl;

import com.gamification.gamificationbackend.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ThymeleafTemplateServiceImpl implements TemplateService {

    private final TemplateEngine templateEngine;

    @Override
    public String processTemplate(String template, Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);
        return templateEngine.process(template, context);
    }
}
