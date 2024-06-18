package com.gamification.gamificationbackend.service;

import com.gamification.gamificationbackend.dto.request.EventRequestDto;
import com.gamification.gamificationbackend.dto.response.EventResponseDto;

import java.util.List;

public interface EventService {

    List<EventResponseDto> getCompanyEvents();
    List<EventResponseDto> getCompletedByPlayerEvents();
    void createEvent(EventRequestDto eventRequestDto);
    void deleteEventById(Long eventId);
    void addPlayerWhoCompletedEvent(Long eventId, Long playerId);
}
