package com.gamification.gamificationbackend.service;

import com.gamification.gamificationbackend.dto.request.EventTypeRequestDto;
import com.gamification.gamificationbackend.dto.response.EventTypeResponseDto;

import java.util.List;

public interface EventTypeService {

    List<EventTypeResponseDto> getCompanyEventTypes();
    void createEventType(EventTypeRequestDto eventTypeRequestDto);
}
