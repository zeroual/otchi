package com.otchi.domain.users.models.mocks;

import com.otchi.domain.users.models.Account;
import com.otchi.domain.users.models.AccountRepository;
import com.otchi.utils.mocks.MockCrudRepository;

import java.util.Optional;
import java.util.stream.StreamSupport;

public class MockAccountRepository extends MockCrudRepository<Account, Long> implements AccountRepository {
    public MockAccountRepository() {
        super(Account.class);
    }

    @Override
    public Optional<Account> findOneByEmail(String email) {
        return StreamSupport.stream(findAll().spliterator(), true)
                .filter(account -> account.getEmail().equals(email))
                .findFirst();
    }
}
