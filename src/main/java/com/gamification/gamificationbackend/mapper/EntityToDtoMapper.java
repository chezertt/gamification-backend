package com.gamification.gamificationbackend.mapper;

import java.util.List;

/**
 * Interface for mappers from entity to response dto
 * @param <E> entity
 * @param <D> response dto
 */
public interface EntityToDtoMapper<E, D> {

    D toDto(E entity);

    List<D> toDtos(List<E> entities);
}
