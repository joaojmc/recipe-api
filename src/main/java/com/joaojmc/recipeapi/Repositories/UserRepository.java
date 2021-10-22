package com.joaojmc.recipeapi.Repositories;

import com.joaojmc.recipeapi.Entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
}