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
    public void followUser(String followerId, Long followingId) {


        User userToFollow = userRepository.findOne(followingId);
        if (userToFollow == null) {
            throw new UserNotFoundException(String.valueOf(followerId));
        }
        if (followerId.equals(userToFollow.getEmail())) {
            throw new UnsupportedOperationException("USER CAN NOT FOLLOW HIM SELF => USER_ID:" + followerId);
        }
        Optional<User> follower = userRepository.findOneByEmail(followerId);
        User user = follower.get();
        user.followUser(userToFollow);
        userRepository.save(user);
    }

}
