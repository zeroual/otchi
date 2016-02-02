package com.otchi.api;

import com.otchi.api.facades.dto.UserDTO;
import com.otchi.application.UserService;
import com.otchi.domaine.users.models.User;
import com.otchi.infrastructure.config.ResourcesPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping(ResourcesPath.ME)
public class CurrentUserResource {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getAccountOfCurrentUser(@AuthenticationPrincipal Principal principal) {
        Optional<User> userOptional = userService.findUserByEmail(principal.getName());
        User user = userOptional.orElseThrow(() -> new IllegalArgumentException("User not found in our database"));
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }

}
