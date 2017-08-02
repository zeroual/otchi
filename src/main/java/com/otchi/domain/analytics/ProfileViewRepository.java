package com.otchi.domain.analytics;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileViewRepository extends CrudRepository<ProfileView, ViewId> {

    Integer countByViewProfileId(Long profileId);

}
