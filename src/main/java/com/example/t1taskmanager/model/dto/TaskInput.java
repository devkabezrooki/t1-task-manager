package com.example.t1taskmanager.model.dto;

import jakarta.annotation.Nonnull;
import lombok.Getter;

import java.util.Date;

@Getter
public class TaskInput {
    @Nonnull
    private String title;
    private String description;
    private Date dueDate;
    private boolean completed;
}
