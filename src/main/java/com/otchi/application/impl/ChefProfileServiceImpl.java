package com.otchi.application.impl;

import com.otchi.application.Chef;
import com.otchi.application.ChefProfileService;
import com.otchi.application.UserService;
import com.otchi.domain.users.exceptions.UserNotFoundException;
import com.otchi.domain.users.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChefProfileServiceImpl implements ChefProfileService {

    private final UserService userService;

    @Autowired
    public ChefProfileServiceImpl(UserService userService) {

        this.userService = userService;
    }

    @Override
    public Chef findChef(Long id) {
        Optional<User> userById = userService.findUserById(id);
        User user = userById.orElseThrow(() -> new UserNotFoundException(id.toString()));
        return new Chef(user.getId(), user.getFirstName(), user.getLastName(), user.picture());
    }
}
