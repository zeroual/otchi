package com.otchi.domain.users.models.mocks;

import com.otchi.domain.users.models.User;
import com.otchi.domain.users.models.UserRepository;
import com.otchi.utils.mocks.MockCrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MockUserRepository extends MockCrudRepository<User, Long> implements UserRepository {
    public MockUserRepository() {
        super(User.class);
    }

    @Override
    public Optional<User> findOneByUsername(String username) {
        return StreamSupport.stream(findAll().spliterator(), true).filter(user -> user.getUsername().equals(username)).findFirst();
    }

    @Override
    public List<User> findAllByIdNotLike(Long id) {
        return StreamSupport.stream(findAll().spliterator(), true).filter(user -> !user.getId().equals(id)).collect(Collectors.toList());
    }

    @Override
    public List<User> findAllByIdNotIn(Collection<Long> ids) {
        return StreamSupport.stream(findAll().spliterator(), true).filter(user -> !ids.contains(user.getId())).collect(Collectors.toList());
    }

}
