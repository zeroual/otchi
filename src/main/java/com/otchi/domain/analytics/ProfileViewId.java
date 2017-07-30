package com.otchi.domain.analytics;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProfileViewId implements Serializable {


    @Column(name = "PROFILE_ID")
    private Long profileId;

    @Column(name = "IP_ADDRESS")
    private String ipAddress;

    private ProfileViewId() {
    }

    public ProfileViewId(Long profileId, String ipAddress) {
        this.profileId = profileId;
        this.ipAddress = ipAddress;
    }

    public Long getProfileId() {
        return profileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileViewId viewId = (ProfileViewId) o;
        return Objects.equals(profileId, viewId.profileId) &&
                Objects.equals(ipAddress, viewId.ipAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileId, ipAddress);
    }

}
