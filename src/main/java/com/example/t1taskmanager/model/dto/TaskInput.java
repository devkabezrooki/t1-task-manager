package com.example.t1taskmanager.model.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class TaskInput {
    @NotBlank
    @Size(max = 500, message = "Длина поля title не может превышать 500 символов")
    private String title;
    @Size(max = 4000, message = "Длина поля description не может превышать 4000 символов")
    private String description;
    @FutureOrPresent(message = "Дата dueDate не может быть в прошлом")
    private Date dueDate;
    private boolean completed;
}
