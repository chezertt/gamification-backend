package com.gamification.gamificationbackend.mapper;

import java.util.List;

/**
 * Interface for mappers from request dto to entity
 * @param <D> request dto
 * @param <E> entity
 */
public interface DtoToEntityMapper<D, E> {

    E toEntity(D dto);

    List<E> toEntities(List<D> dtos);

}
