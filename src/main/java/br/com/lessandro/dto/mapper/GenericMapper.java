package br.com.lessandro.dto.mapper;

import java.util.List;

import org.mapstruct.MappingTarget;

/**
 * A classe abstrata responsável por implementa os métodos genéricos para o GenericMapper.
 * @param <D> classe DTO
 * @param <E> classe Entidade
 */
public interface GenericMapper<E, D> {

    D entityToDto(E entity);

    E dtoToEntity(D dto);
    
    void updateEntity(@MappingTarget E entity, D dto);

    List<D> entitiesToDtos(List<E> entities);

    List<E> dtosToEntities(List<D> dtos);
}
