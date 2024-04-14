package com.example.t1taskmanager.exception.model;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.text.MessageFormat;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(@Nullable Long id, @Nonnull Class clazz) {
        super(MessageFormat.format("Entity {0} with id {1} was not found.", clazz.getSimpleName(), String.valueOf(id)));
    }
}
