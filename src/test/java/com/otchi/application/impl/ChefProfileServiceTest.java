package com.otchi.application.impl;

import com.otchi.application.*;
import com.otchi.domain.kitchen.Chef;
import com.otchi.domain.users.exceptions.UserNotFoundException;
import com.otchi.domain.users.models.User;
import com.otchi.domain.users.models.UserBuilder;
import org.junit.Test;
import org.mockito.Mockito;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class ChefProfileServiceTest {

    private UserService userService = Mockito.mock(UserService.class);
    private FeedFetcherService feedFetchService = Mockito.mock(FeedFetcherService.class);
    private ChefProfileService chefProfileService = new ChefProfileServiceImpl(userService, feedFetchService);

    @Test
    public void shouldMapUserInfoInToChef() {
        User user = UserBuilder.asUser()
                .withFirstName("firstName")
                .withLastName("lastName")
                .withPicture("picture")
                .build();
        setField(user, "id", 1L);

        when(userService.findUserById(1L)).thenReturn(of(user));

        Chef chef = chefProfileService.findChef(1L);
        assertThat(chef.getId()).isEqualTo(1);
        assertThat(chef.getFirstName()).isEqualTo("firstName");
        assertThat(chef.getLastName()).isEqualTo("lastName");
        assertThat(chef.getPicture()).isEqualTo("picture");
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldThrowExceptionIfChefNotExist() {
        when(userService.findUserById(1L)).thenReturn(empty());
        chefProfileService.findChef(1L);
    }

}