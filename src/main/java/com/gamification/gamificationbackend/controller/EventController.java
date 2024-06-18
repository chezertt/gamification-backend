package com.gamification.gamificationbackend.controller;

import com.gamification.gamificationbackend.dto.request.EventRequestDto;
import com.gamification.gamificationbackend.dto.response.EventResponseDto;
import com.gamification.gamificationbackend.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/events")
@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<EventResponseDto>> getCompanyEvents() {
        return ResponseEntity.ok(eventService.getCompanyEvents());
    }

    @PreAuthorize("hasRole('PLAYER')")
    @GetMapping("/completed")
    public ResponseEntity<List<EventResponseDto>> getCompletedByPlayerEvents() {
        return ResponseEntity.ok(eventService.getCompletedByPlayerEvents());
    }

    @PreAuthorize("hasRole('COMPANY')")
    @PostMapping
    public ResponseEntity<Void> createEvent(@Valid @RequestBody EventRequestDto eventRequestDto) {
        eventService.createEvent(eventRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('COMPANY')")
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEventById(@PathVariable Long eventId) {
        eventService.deleteEventById(eventId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('COMPANY')")
    @PostMapping("/{eventId}/players-who-completed-event/{playerId}")
    public ResponseEntity<Void> addPlayerWhoCompletedEvent(@PathVariable Long eventId, @PathVariable Long playerId) {
        eventService.addPlayerWhoCompletedEvent(eventId, playerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
