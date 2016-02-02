package com.otchi.application;

import com.otchi.domaine.users.models.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserByEmail(String email);
}
