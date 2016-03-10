package com.otchi.application;


import com.otchi.domaine.users.models.User;

import java.util.List;

public interface FollowUserService {

    void followUser(String followerId, Long followingId);


    List<User> getAllPossibleFollowers(String userId);
}
