package com.example.t1taskmanager.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskResponse {
    @JsonProperty(value = "Заголовок", required = true)
    private String title;
    @JsonProperty("Описание")
    private String description;
    @JsonProperty("Дедлайн")
    private String dueDate;
    @JsonProperty(value = "Выполнено", defaultValue = "false", required = true)
    private boolean completed;
}
