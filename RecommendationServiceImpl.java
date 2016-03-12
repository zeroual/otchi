package com.otchi.application.impl;

import com.otchi.application.RecommendationService;
import com.otchi.domain.users.exceptions.UserNotFoundException;
import com.otchi.domain.users.models.User;
import com.otchi.domain.users.models.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
//FIXME ADD Tests to this class
public class RecommendationServiceImpl implements RecommendationService {

    private UserRepository userRepository;

    @Autowired
    public RecommendationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getRecommendedFollowingsFor(String userId) {
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
