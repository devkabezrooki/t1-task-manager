package com.example.t1taskmanager.service;

import com.example.t1taskmanager.exception.model.EntityNotFoundException;
import com.example.t1taskmanager.model.Task;
import com.example.t1taskmanager.model.dto.TaskInput;
import com.example.t1taskmanager.model.dto.TaskResponse;
import com.example.t1taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    TaskService taskService;

    private static Task expectedTask;
    private static TaskInput taskInput;

    @BeforeAll
    public static void initFields() {
        expectedTask = new Task();
        expectedTask.setTitle("Task1");
        expectedTask.setDescription("Desc1");
        expectedTask.setDueDate(new Date(2024, 04, 16));
        expectedTask.setCompleted(true);

        taskInput = new TaskInput()
                .setTitle("Task1")
                .setDescription("Desc1")
                .setDueDate(new Date(2024, 04, 16))
                .setCompleted(true);
    }

    @Test
    public void testGetAllTasks_Success() {
        List<Task> expectedTasks = new ArrayList<>();
        expectedTasks.add(expectedTask);
        Task task2 = new Task();
        task2.setTitle("Task2");
        task2.setDescription("Desc2");
        task2.setCompleted(true);
        expectedTasks.add(task2);

        when(taskRepository.findAll()).thenReturn(expectedTasks);

        List<TaskResponse> result = taskService.getAllTasks();

        assertEquals(expectedTasks.size(), result.size());
        assertTrue(expectedTasks.stream().map(Task::getTitle).collect(Collectors.toList()).containsAll(result.stream().map(TaskResponse::getTitle).collect(Collectors.toList())));
        assertTrue(expectedTasks.stream().map(Task::getDescription).collect(Collectors.toList()).containsAll(result.stream().map(TaskResponse::getDescription).collect(Collectors.toList())));
    }

    @Test
    public void testGetTask_Success() {
        Long taskId = 1L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.ofNullable(expectedTask));

        TaskResponse result = taskService.getTask(taskId);

        assertEquals(expectedTask.getTitle(), result.getTitle());
        assertEquals(expectedTask.getDescription(), result.getDescription());
        assertEquals(expectedTask.getDueDate(), result.getDueDate());
        assertEquals(expectedTask.isCompleted(), result.isCompleted());
    }

    @Test
    public void testGetTask_ThrowEntityNotFoundException() {
        Long taskId = 1001L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            taskService.getTask(taskId);
        });
    }

    @Test
    public void testCreateTask_Success() {
        when(taskRepository.save(any(Task.class))).thenReturn(expectedTask);

        TaskResponse result = taskService.createTask(taskInput);

        assertEquals(expectedTask.getTitle(), result.getTitle());
        assertEquals(expectedTask.getDescription(), result.getDescription());
        assertEquals(expectedTask.getDueDate(), result.getDueDate());
        assertEquals(expectedTask.isCompleted(), result.isCompleted());
    }

    @Test
    public void testUpdateTask_Success() {
        Long taskId = 1L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.ofNullable(expectedTask));

        TaskResponse result = taskService.updateTask(taskId, taskInput);

        assertEquals(expectedTask.getTitle(), result.getTitle());
        assertEquals(expectedTask.getDescription(), result.getDescription());
        assertEquals(expectedTask.getDueDate(), result.getDueDate());
        assertEquals(expectedTask.isCompleted(), result.isCompleted());

        result = taskService.updateTask(taskId, null);

        assertEquals(expectedTask.getTitle(), result.getTitle());
        assertEquals(expectedTask.getDescription(), result.getDescription());
        assertEquals(expectedTask.getDueDate(), result.getDueDate());
        assertEquals(expectedTask.isCompleted(), result.isCompleted());
    }

    @Test
    public void testUpdateTask_ThrowEntityNotFoundException() {
        Long taskId = 1001L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            taskService.updateTask(taskId, null);
        });
        assertThrows(EntityNotFoundException.class, () -> {
            taskService.updateTask(taskId, taskInput);
        });
    }

    @Test
    public void testDeleteTask_Success() {
        Long taskId = 1L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.ofNullable(expectedTask));

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).delete(expectedTask);
    }

    @Test
    public void testDeleteTask_ThrowEntityNotFoundException() {
        Long taskId = 1001L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            taskService.deleteTask(taskId);
        });
    }
}
