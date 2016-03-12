package com.otchi.application;


import com.otchi.domain.users.models.User;

import java.util.List;

public interface RecommendationService {

    List<User> getRecommendedFollowingsFor(String userId);
}
