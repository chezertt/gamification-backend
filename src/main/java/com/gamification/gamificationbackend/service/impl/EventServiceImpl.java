package com.gamification.gamificationbackend.service.impl;

import com.gamification.gamificationbackend.dto.request.EventRequestDto;
import com.gamification.gamificationbackend.dto.response.EventResponseDto;
import com.gamification.gamificationbackend.entity.*;
import com.gamification.gamificationbackend.enumeration.ErrorType;
import com.gamification.gamificationbackend.exception.BadRequestException;
import com.gamification.gamificationbackend.mapper.impl.EventMapper;
import com.gamification.gamificationbackend.repository.EventRepository;
import com.gamification.gamificationbackend.service.EventService;
import com.gamification.gamificationbackend.service.JwtService;
import com.gamification.gamificationbackend.service.PlayerService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final JwtService jwtService;
    private final EntityManager entityManager;
    private final PlayerService playerService;

    @Override
    public List<EventResponseDto> getCompanyEvents() {
        Long companyId = jwtService.getCompanyId();
        return eventMapper.toDtos(eventRepository.findAllByCompanyIdOrderByCreatedAtDesc(companyId));
    }

    @Override
    public List<EventResponseDto> getCompletedByPlayerEvents() {
        Long playerId = jwtService.getPlayerId();
        return eventMapper.toDtos(eventRepository.findAllByPlayersWhoCompletedEvent_IdOrderByCreatedAtDesc(playerId));
    }

    @Transactional
    @Override
    public void createEvent(EventRequestDto eventRequestDto) {
        Long companyId = jwtService.getCompanyId();
        if (eventRepository.existsByNameIgnoreCaseAndCompanyId(eventRequestDto.getName(), companyId)) {
            throw new BadRequestException(ErrorType.CLIENT, "У вас уже есть созданное событие с таким названием, название события должно быть уникальным");
        }
        List<Bonus> bonuses = eventRequestDto.getBonusIds().stream()
                .map(bonusId -> entityManager.getReference(Bonus.class, bonusId))
                .toList();
        Event newEvent = Event.builder()
                .company(entityManager.getReference(Company.class, companyId))
                .name(eventRequestDto.getName())
                .description(eventRequestDto.getDescription())
                .points(eventRequestDto.getPoints())
                .coins(eventRequestDto.getCoins())
                .bonuses(bonuses)
                .build();
        Long typeId = eventRequestDto.getTypeId();
        if (typeId != null) {
            newEvent.setType(entityManager.getReference(EventType.class, typeId));
        }
        eventRepository.save(newEvent);
    }

    @Transactional
    @Override
    public void deleteEventById(Long eventId) {
        Event event = getEventByIdOrElseThrow(eventId);
        if (CollectionUtils.isEmpty(event.getPlayersWhoCompletedEvent())) {
            eventRepository.deleteById(eventId);
        } else {
            throw new BadRequestException(ErrorType.CLIENT, "Событие невозможно удалить, так как уже есть сотрудники, завершившие его");
        }
    }

    @Transactional
    @Override
    public void addPlayerWhoCompletedEvent(Long eventId, Long playerId) {
        Event event = getEventByIdOrElseThrow(eventId);
        if (event.getPlayersWhoCompletedEvent().contains(entityManager.getReference(Player.class, playerId))) {
            throw new BadRequestException(ErrorType.CLIENT, "Выбранный сотрудник уже завершил данное событие и получил награду");
        }
        Player player = playerService.rewardPlayerAndGet(playerId, event.getPoints(), event.getCoins(), event.getBonuses());
        event.getPlayersWhoCompletedEvent().add(player);
        eventRepository.save(event);
    }

    private Event getEventByIdOrElseThrow(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new BadRequestException(ErrorType.TECH, String.format("Нет события с id = %s", eventId)));
    }
}
