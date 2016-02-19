package com.otchi.domaine.users.models.mocks;

import com.otchi.domaine.users.models.User;
import com.otchi.domaine.users.models.UserRepository;
import com.otchi.utils.mocks.MockCrudRepository;

import java.util.Optional;
import java.util.stream.StreamSupport;

public class MockUserRepository extends MockCrudRepository<User, String> implements UserRepository {
    public MockUserRepository() {
        super(User.class);
    }

    @Override
    public Optional<User> findOneByEmail(String email) {
        return StreamSupport.stream(findAll().spliterator(), true).filter(user -> user.getEmail().equals(email)).findFirst();
    }
}
