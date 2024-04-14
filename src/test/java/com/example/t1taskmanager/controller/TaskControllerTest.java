package com.example.t1taskmanager.controller;

import com.example.t1taskmanager.exception.model.EntityNotFoundException;
import com.example.t1taskmanager.model.Task;
import com.example.t1taskmanager.model.dto.TaskInput;
import com.example.t1taskmanager.model.dto.TaskResponse;
import com.example.t1taskmanager.service.TaskService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private static TaskResponse expectedTask;
    private static TaskInput taskInput;

    @BeforeAll
    public static void initFields() {
        expectedTask = new TaskResponse()
                .setTitle("Task1")
                .setDescription("Desc1")
                .setDueDate(new Date(2024, 04, 16))
                .setCompleted(true);
        taskInput = new TaskInput()
                .setTitle("Task1")
                .setDescription("Desc1")
                .setDueDate(new Date(2024, 04, 16))
                .setCompleted(true);
    }

    @Test
    public void testGetAllTasks_Success() {
        List<TaskResponse> expectedTasks = new ArrayList<>();
        expectedTasks.add(expectedTask);
        expectedTasks.add(
                new TaskResponse()
                        .setTitle("Task2")
                        .setDueDate(new Date(2024, 04, 16))
                        .setCompleted(false)
        );
        expectedTasks.add(
                new TaskResponse()
                        .setTitle("Task3")
                        .setDescription("Desc3")
                        .setCompleted(false)
        );

        when(taskService.getAllTasks()).thenReturn(expectedTasks);

        ResponseEntity<List<TaskResponse>> response = taskController.getAllTasks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTasks.size(), response.getBody().size());
        assertTrue(expectedTasks.containsAll(response.getBody()));
    }

    @Test
    public void testGetTask_Success() {
        Long taskId = 1L;

        when(taskService.getTask(taskId)).thenReturn(expectedTask);

        ResponseEntity<TaskResponse> response = taskController.getTask(taskId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedTask.getTitle(), response.getBody().getTitle());
        assertEquals(expectedTask.getDescription(), response.getBody().getDescription());
        assertEquals(expectedTask.getDueDate(), response.getBody().getDueDate());
        assertEquals(expectedTask.isCompleted(), response.getBody().isCompleted());
    }

    @Test
    public void testGetTask_ThrowEntityNotFoundException() {
        Long taskId = 1001L;

        when(taskService.getTask(taskId)).thenThrow(new EntityNotFoundException(taskId, Task.class));

        assertThrows(EntityNotFoundException.class, () -> {
            taskController.getTask(taskId);
        });
    }

    @Test
    public void testCreateTask_Success() {
        when(taskService.createTask(taskInput)).thenReturn(expectedTask);

        ResponseEntity<TaskResponse> response = taskController.createTask(taskInput);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedTask.getTitle(), response.getBody().getTitle());
        assertEquals(expectedTask.getDescription(), response.getBody().getDescription());
        assertEquals(expectedTask.getDueDate(), response.getBody().getDueDate());
        assertEquals(expectedTask.isCompleted(), response.getBody().isCompleted());
    }

    @Test
    public void testUpdateTask() {
        Long taskId = 1L;

        when(taskService.updateTask(taskId, null)).thenReturn(expectedTask);
        when(taskService.updateTask(taskId, taskInput)).thenReturn(expectedTask);

        ResponseEntity<TaskResponse> response = taskController.updateTask(taskId, taskInput);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedTask.getTitle(), response.getBody().getTitle());
        assertEquals(expectedTask.getDescription(), response.getBody().getDescription());
        assertEquals(expectedTask.getDueDate(), response.getBody().getDueDate());
        assertEquals(expectedTask.isCompleted(), response.getBody().isCompleted());

        response = taskController.updateTask(taskId, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedTask.getTitle(), response.getBody().getTitle());
        assertEquals(expectedTask.getDescription(), response.getBody().getDescription());
        assertEquals(expectedTask.getDueDate(), response.getBody().getDueDate());
        assertEquals(expectedTask.isCompleted(), response.getBody().isCompleted());
    }

    @Test
    public void testUpdateTask_ThrowEntityNotFoundException() {
        Long taskId = 1001L;

        when(taskService.updateTask(taskId, null)).thenThrow(new EntityNotFoundException(taskId, Task.class));
        when(taskService.updateTask(taskId, taskInput)).thenThrow(new EntityNotFoundException(taskId, Task.class));

        assertThrows(EntityNotFoundException.class, () -> {
            taskController.updateTask(taskId, null);
        });
        assertThrows(EntityNotFoundException.class, () -> {
            taskController.updateTask(taskId, taskInput);
        });
    }

    @Test
    public void testDeleteTask_Success() {
        Long taskId = 1L;

        ResponseEntity<?> response = taskController.deleteTask(taskId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        verify(taskService, times(1)).deleteTask(taskId);
    }

    @Test
    public void testDeleteTask_ThrowEntityNotFoundException() {
        Long taskId = 1001L;

        doThrow(new EntityNotFoundException(taskId, Task.class)).when(taskService).deleteTask(taskId);

        assertThrows(EntityNotFoundException.class, () -> {
            taskController.deleteTask(taskId);
        });
    }
}
