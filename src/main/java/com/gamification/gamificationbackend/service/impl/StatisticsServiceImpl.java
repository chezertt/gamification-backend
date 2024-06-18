package com.gamification.gamificationbackend.service.impl;

import com.gamification.gamificationbackend.dto.response.StatisticsResponseDto;
import com.gamification.gamificationbackend.entity.Statistics;
import com.gamification.gamificationbackend.entity.Company;
import com.gamification.gamificationbackend.mapper.impl.StatisticsMapper;
import com.gamification.gamificationbackend.repository.StatisticsRepository;
import com.gamification.gamificationbackend.repository.CompanyRepository;
import com.gamification.gamificationbackend.service.StatisticsService;
import com.gamification.gamificationbackend.service.JwtService;
import com.gamification.gamificationbackend.util.DateTimeUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsRepository statisticsRepository;
    private final CompanyRepository companyRepository;
    private final StatisticsMapper statisticsMapper;
    private final JwtService jwtService;

    public static final String CRON_EVERY_DAY_AT_0 = "0 0 0 * * ?";

    @Override
    public List<StatisticsResponseDto> getStatisticsForLast7Days() {
        Long companyId = jwtService.getCompanyId();
        return statisticsMapper.toDtos(statisticsRepository.findTop7ByCompanyIdOrderByCreatedAtDesc(companyId));
    }

    @Scheduled(cron = CRON_EVERY_DAY_AT_0)
    @Transactional
    void logDailyAnalyticsForAllCompanies() {
        for (Company company : companyRepository.findAll()) {
            Long companyId = company.getId();
            OffsetDateTime nowMinusOneDay = DateTimeUtil.nowInUTC().minusDays(1);
            Integer completedEventsForLastDay = Math.toIntExact(statisticsRepository.countCompletedEventsForLastDayByCompanyId(companyId, nowMinusOneDay));
            Integer obtainedBonusesForLastDay = Math.toIntExact(statisticsRepository.countObtainedBonusesForLastDayByCompanyId(companyId, nowMinusOneDay));
            Statistics statisticsForLastDay = Statistics.builder()
                    .company(company)
                    .completedEvents(completedEventsForLastDay)
                    .obtainedBonuses(obtainedBonusesForLastDay)
                    .build();
            statisticsRepository.save(statisticsForLastDay);
            log.info("Saved analytics for company {}. Completed event for last day = {}, obtained bonuses for last day = {}",
                    company.getName(), completedEventsForLastDay, obtainedBonusesForLastDay);
        }
    }
}
