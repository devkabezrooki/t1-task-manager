package com.example.t1taskmanager.controller;

import com.example.t1taskmanager.model.dto.TaskInput;
import com.example.t1taskmanager.model.dto.TaskResponse;
import com.example.t1taskmanager.service.TaskService;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(@Nonnull TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping()
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(taskService.getTask(id));
    }

    @PostMapping()
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskInput taskInput) {
        return ResponseEntity.ok(taskService.createTask(taskInput));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable(value = "id") Long id,
                                        @RequestBody(required = false) TaskInput taskInput) {
        return ResponseEntity.ok(taskService.updateTask(id, taskInput));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable(value = "id") Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}
