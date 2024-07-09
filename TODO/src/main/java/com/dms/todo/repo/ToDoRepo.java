package com.dms.todo.repo;

import com.dms.todo.entity.ToDo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ToDoRepo extends MongoRepository<ToDo,String> {

    Optional<ToDo> findById(String id);
}
