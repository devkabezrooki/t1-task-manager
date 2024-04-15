package com.example.t1taskmanager.service;

import com.example.t1taskmanager.model.Task;
import com.example.t1taskmanager.model.dto.TaskInput;
import com.example.t1taskmanager.model.dto.TaskResponse;

import java.text.SimpleDateFormat;

public class TaskMapper {

    public static Task createTaskFromInput(TaskInput taskInput) {
        Task task = new Task();
        task.setTitle(taskInput.getTitle());
        task.setDescription(taskInput.getDescription());
        task.setDueDate(taskInput.getDueDate());
        task.setCompleted(taskInput.isCompleted());
        return task;
    }

    public static Task updateTaskFromInput(Task task, TaskInput taskInput) {
        String title = taskInput.getTitle();
        if (title != null) {
            task.setTitle(title);
        }
        task.setDescription(taskInput.getDescription());
        task.setDueDate(taskInput.getDueDate());
        task.setCompleted(taskInput.isCompleted());
        return task;
    }

    public static TaskResponse createTaskResponseFromTask(Task task) {
        String dueDateString = task.getDueDate() != null
                ? new SimpleDateFormat("dd.MM.yyyy").format(task.getDueDate())
                : null;
        return new TaskResponse()
                .setTitle(task.getTitle())
                .setDescription(task.getDescription())
                .setDueDate(dueDateString)
                .setCompleted(task.isCompleted());
    }
}
