package com.example.t1taskmanager.model.dto;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class TaskInput {
    @Nonnull
    private String title;
    private String description;
    private Date dueDate;
    private boolean completed;
}
