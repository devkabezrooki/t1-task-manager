package com.example.t1taskmanager.service;

import com.example.t1taskmanager.exception.model.EntityNotFoundException;
import com.example.t1taskmanager.model.Task;
import com.example.t1taskmanager.model.dto.TaskInput;
import com.example.t1taskmanager.model.dto.TaskResponse;
import com.example.t1taskmanager.repository.TaskRepository;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(@Nonnull TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(t -> TaskMapper.createTaskResponseFromTask(t))
                .collect(Collectors.toList());
    }

    public TaskResponse getTask(Long id) {
        return TaskMapper.createTaskResponseFromTask(getByIdOrThrow(id));
    }

    @Transactional
    public TaskResponse createTask(TaskInput taskInput) {
        Task task = TaskMapper.createTaskFromInput(taskInput);
        taskRepository.save(task);
        return TaskMapper.createTaskResponseFromTask(task);
    }

    @Transactional
    public TaskResponse updateTask(Long id, @Nullable TaskInput taskInput) {
        Task task = getByIdOrThrow(id);
        if (taskInput != null) {
            TaskMapper.updateTaskFromInput(task, taskInput);
        } else {
            task.setCompleted(true);
        }
        return TaskMapper.createTaskResponseFromTask(task);
    }

    @Transactional
    public void deleteTask(Long id) {
        Task task = getByIdOrThrow(id);
        taskRepository.delete(task);
    }

    @Transactional
    public Task getByIdOrThrow(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Task.class));
    }
}
