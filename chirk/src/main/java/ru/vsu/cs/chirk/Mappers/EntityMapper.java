package ru.vsu.cs.chirk.Mappers;

interface EntityMapper <T, E> {

    T convertToDTO(E entity);
}

