package com.gamification.gamificationbackend.controller;

import com.gamification.gamificationbackend.dto.request.EventTypeRequestDto;
import com.gamification.gamificationbackend.dto.response.EventTypeResponseDto;
import com.gamification.gamificationbackend.service.EventTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/event-types")
@RestController
@RequiredArgsConstructor
public class EventTypeController {

    private final EventTypeService eventTypeService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<EventTypeResponseDto>> getCompanyEventTypes() {
        return ResponseEntity.ok(eventTypeService.getCompanyEventTypes());
    }

    @PreAuthorize("hasRole('COMPANY')")
    @PostMapping
    public ResponseEntity<Void> createEventTypes(@RequestBody EventTypeRequestDto eventTypeRequestDto) {
        eventTypeService.createEventType(eventTypeRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
