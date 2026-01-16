package org.example.repositories;

import org.example.models.User;

import java.util.ArrayList;


public interface UserRepository {
    ArrayList<User> getAll();

    User getById(String id);

    User getByEmail(String email);

    void save(User user);

    boolean existsByEmail(String email);
}
