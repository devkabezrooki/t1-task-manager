package com.example.t1taskmanager.exception.model;

import jakarta.annotation.Nonnull;

import java.text.MessageFormat;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(@Nonnull Long id, @Nonnull Class clazz) {
        super(MessageFormat.format("Сущность {0} с id {1} не обнаружена.", clazz.getSimpleName(), String.valueOf(id)));
    }
}
