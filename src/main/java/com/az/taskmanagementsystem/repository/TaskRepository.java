package com.az.taskmanagementsystem.repository;

import com.az.taskmanagementsystem.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query(value = "SELECT * FROM task t WHERE t.title LIKE %:keyword% OR t.description LIKE %:keyword%", nativeQuery = true)
    List<Task> searchByKeyword(@Param("keyword") String keyword);
}
