package com.example.springboot;

import com.example.springboot.MenuItem;
import org.springframework.data.repository.CrudRepository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface MenuItemRepository extends CrudRepository<MenuItem, Integer> {
    public MenuItem findByName(String name);
    public Boolean existsByName(String name);

}