package com.otchi.application;

import com.otchi.domain.users.models.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserByEmail(String email);
}
