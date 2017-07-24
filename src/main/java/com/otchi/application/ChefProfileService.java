package com.otchi.application;

import com.otchi.domain.kitchen.Chef;

import java.util.List;

public interface ChefProfileService {

    Chef findChef(Long id);

    List<Feed> fetchChefFeeds(Long id);
}
