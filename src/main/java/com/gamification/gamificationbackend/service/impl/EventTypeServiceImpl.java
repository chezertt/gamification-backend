package com.gamification.gamificationbackend.service.impl;

import com.gamification.gamificationbackend.dto.request.EventTypeRequestDto;
import com.gamification.gamificationbackend.dto.response.EventTypeResponseDto;
import com.gamification.gamificationbackend.entity.Company;
import com.gamification.gamificationbackend.entity.EventType;
import com.gamification.gamificationbackend.enumeration.ErrorType;
import com.gamification.gamificationbackend.exception.BadRequestException;
import com.gamification.gamificationbackend.mapper.impl.EventTypeMapper;
import com.gamification.gamificationbackend.repository.EventTypeRepository;
import com.gamification.gamificationbackend.service.EventTypeService;
import com.gamification.gamificationbackend.service.JwtService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventTypeServiceImpl implements EventTypeService {

    private final EventTypeRepository eventTypeRepository;
    private final EventTypeMapper eventTypeMapper;
    private final JwtService jwtService;
    private final EntityManager entityManager;

    @Override
    public List<EventTypeResponseDto> getCompanyEventTypes() {
        Long companyId = jwtService.getCompanyId();
        return eventTypeMapper.toDtos(eventTypeRepository.findAllByCompanyIdOrderByCreatedAtDesc(companyId));
    }

    @Transactional
    @Override
    public void createEventType(EventTypeRequestDto eventTypeRequestDto) {
        Long companyId = jwtService.getCompanyId();
        if (eventTypeRepository.existsByNameIgnoreCaseAndCompanyId(eventTypeRequestDto.getName(), companyId)) {
            throw new BadRequestException(ErrorType.CLIENT, "У вас уже есть созданный тип событий с таким названием, название типа событий должно быть уникальным");
        }
        EventType newEvenType = EventType.builder()
                .company(entityManager.getReference(Company.class, companyId))
                .name(eventTypeRequestDto.getName())
                .build();
        eventTypeRepository.save(newEvenType);
    }
}
