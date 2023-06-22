package com.az.taskmanagementsystem.repository;

import com.az.taskmanagementsystem.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}
