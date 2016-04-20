package com.otchi.application.impl;

import com.otchi.application.FollowingService;
import com.otchi.domain.users.exceptions.UserNotFoundException;
import com.otchi.domain.users.models.User;
import com.otchi.domain.users.models.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FollowingServiceImpl implements FollowingService {

    private UserRepository userRepository;

    @Autowired
    public FollowingServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void followUser(String followerUsername, Long followingId) {


        User userToFollow = userRepository.findOne(followingId);
        if (userToFollow == null) {
            throw new UserNotFoundException(String.valueOf(followerUsername));
        }
        if (followerUsername.equals(userToFollow.getUsername())) {
            throw new UnsupportedOperationException("USER CAN NOT FOLLOW HIM SELF => USER_ID:" + followerUsername);
        }
        Optional<User> follower = userRepository.findOneByUsername(followerUsername);
        User user = follower.get();
        user.followUser(userToFollow);
        userRepository.save(user);
    }

}
