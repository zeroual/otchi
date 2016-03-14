package com.otchi.application.impl;

import com.otchi.application.FollowUserService;
import com.otchi.domaine.users.exceptions.UserNotFoundException;
import com.otchi.domaine.users.models.User;
import com.otchi.domaine.users.models.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FollowUserServiceImpl implements FollowUserService {

    private UserRepository userRepository;

    @Autowired
    public FollowUserServiceImpl(UserRepository userRepository) {
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


    @Override
    public List<User> getAllPossibleFollowers(String userId) {
        Optional<User> userOptional = userRepository.findOneByEmail(userId);

        if (!userOptional.isPresent()) {
            throw new UserNotFoundException(String.valueOf(userId));
        }
        User user = userOptional.get();

        List<User> listOfPossibleFollower = userRepository.findAllByIdNotLike(user.getId());
        Set<User> listOfExistingFolowers = user.getFollowing();
        Set<String> listOfFollowers = StreamSupport.stream(listOfExistingFolowers.spliterator(), true).map(User::getEmail).collect(Collectors.toSet());
        return StreamSupport.stream(listOfPossibleFollower.spliterator(), true).filter(u -> !listOfFollowers.contains(u.getEmail())).collect(Collectors.toList());
    }

}
