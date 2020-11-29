package com.example.springboot.Repo;

import com.example.springboot.Domain.MenuItem;
import org.springframework.data.repository.CrudRepository;

public interface MenuItemRepository extends CrudRepository<MenuItem, Integer> {
    public MenuItem findByName(String name);
    public Boolean existsByName(String name);

}