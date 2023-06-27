package com.az.taskmanagementsystem;

import com.az.taskmanagementsystem.exception.NotFoundException;
import com.az.taskmanagementsystem.model.Task;
import com.az.taskmanagementsystem.repository.TaskRepository;
import com.az.taskmanagementsystem.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void testCreateTask() {
        Task task = new Task();
        task.setTitle("Sample Task");
        task.setDescription("Task description");
        task.setDueDate(LocalDate.now());
        task.setStatus("in progress");

        when(taskRepository.save(task)).thenReturn(task);

        Task createdTask = taskService.createTask(task);

        verify(taskRepository, times(1)).save(task);

        assertEquals(task.getTitle(), createdTask.getTitle());
        assertEquals(task.getDescription(), createdTask.getDescription());
        assertEquals(task.getDueDate(), createdTask.getDueDate());
        assertEquals(task.getStatus(), createdTask.getStatus());
    }

    @Test
    public void testGetAllTasks() {
        Task task1 = new Task();
        task1.setTitle("Task 1");
        Task task2 = new Task();
        task2.setTitle("Task 2");
        List<Task> tasks = Arrays.asList(task1, task2);

        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> retrievedTasks = taskService.getAllTasks();

        verify(taskRepository, times(1)).findAll();

        assertEquals(tasks.size(), retrievedTasks.size());
        assertEquals(task1.getTitle(), retrievedTasks.get(0).getTitle());
        assertEquals(task2.getTitle(), retrievedTasks.get(1).getTitle());
    }

    @Test
    public void testGetTaskById_ExistingId_ReturnsTask() {
        Task task = new Task();
        task.setId(1);
        task.setTitle("Sample Task");

        when(taskRepository.findById(1)).thenReturn(Optional.of(task));

        Optional<Task> retrievedTask = taskService.getTaskById(1);

        verify(taskRepository, times(1)).findById(1);

        assertEquals(task.getTitle(), retrievedTask.get().getTitle());
    }


    @Test
    public void testUpdateTask_NonExistingId_ThrowsNotFoundException() {
        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Task");

        when(taskRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> taskService.updateTask(1, updatedTask));

        verify(taskRepository, times(1)).findById(1);
        verify(taskRepository, never()).save(any());
    }


}
