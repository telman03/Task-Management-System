package com.az.taskmanagementsystem.service;

import com.az.taskmanagementsystem.exception.NotFoundException;
import com.az.taskmanagementsystem.model.Task;
import com.az.taskmanagementsystem.model.enums.TaskStatus;
import com.az.taskmanagementsystem.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task createTask(Task task) {
        TaskStatus status = task.getStatus();
        if (status != TaskStatus.in_progress && status != TaskStatus.ready_for_test && status != TaskStatus.done) {
            throw new IllegalArgumentException("Invalid task status. Allowed values are: in_progress, ready_for_test, done");
        }else {
            return taskRepository.save(task);
        }

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
    private boolean isValidStatus(String status) {
        return status.equals("in progress") || status.equals("ready for test") || status.equals("done");
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    public List<Task> searchTasks(String keyword) {
        return taskRepository.searchByKeyword(keyword);
    }
}
