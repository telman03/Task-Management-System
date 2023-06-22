package com.az.taskmanagementsystem.service;

import com.az.taskmanagementsystem.exception.NotFoundException;
import com.az.taskmanagementsystem.model.Task;
import com.az.taskmanagementsystem.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Optional<Task> getTaskById(Integer id) {
        return taskRepository.findById(id);
    }

    public Task updateTask(Integer id, Task task) {
        Optional<Task> existingTask = taskRepository.findById(id);
        if (existingTask.isPresent()) {
            task.setId(id);
            return taskRepository.save(task);
        } else {
            throw new NotFoundException("Task not found with ID: " + id);
        }
    }

    public void deleteTask(Integer id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else {
            throw new NotFoundException("Task not found with ID: " + id);
        }
    }
}
