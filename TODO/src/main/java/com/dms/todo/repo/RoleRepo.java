package com.dms.todo.repo;


import com.dms.todo.entity.Role;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepo extends MongoRepository<Role, ObjectId> {

    Optional<Role> findByName(String name);
}
