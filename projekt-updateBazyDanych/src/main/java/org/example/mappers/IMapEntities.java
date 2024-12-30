package org.example.mappers;

public interface IMapEntities<TDto, TEntity> {

    TEntity map(TDto dto);
    TEntity map(TDto dto, TEntity entity);
}
