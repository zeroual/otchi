package com.otchi.domaine.users.models;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the User entity.
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findOneByEmail(String email);
}