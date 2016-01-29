package com.otchi.api.facades.dto;

public interface DTO<T> {

    T toDomain();

    void extractFromDomain(T model);
}
