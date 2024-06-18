package com.gamification.gamificationbackend.mapper.impl;

import com.gamification.gamificationbackend.dto.response.CompanyResponseDto;
import com.gamification.gamificationbackend.entity.Company;
import com.gamification.gamificationbackend.mapper.EntityToDtoMapper;
import org.mapstruct.Mapper;

@Mapper
public interface CompanyMapper extends EntityToDtoMapper<Company, CompanyResponseDto> {
}
