package com.otchi.domain.users.models;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the User entity.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findOneByEmail(String email);

    List<User> findAllByIdNotLike(Long id);

    List<User> findAllByIdNotIn(Collection<Long> ids);
}