package com.dms.todo.repo;


import com.dms.todo.entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<UserEntity, ObjectId> {

    Optional<UserEntity> findByUsername(String username);
    Boolean existsByUsername(String username);
}
