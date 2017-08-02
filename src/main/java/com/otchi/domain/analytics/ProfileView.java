package com.otchi.domain.analytics;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PROFILE_VIEW")
public class ProfileView {

    @EmbeddedId
    private ProfileViewId view;


    private ProfileView() {
    }

    public ProfileView(Long profileId, String ipAddress) {
        this.view = new ProfileViewId(profileId, ipAddress);
    }

    public Long getProfileId(){
        return view.getProfileId();
    }

}
