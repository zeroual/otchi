package com.otchi.domain.analytics;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ViewId implements Serializable {


    @Column(name = "POST_ID")
    private Long postId;

    @Column(name = "IP_ADDRESS")
    private String ipAddress;

    private ViewId() {
    }

    public ViewId(Long postId, String ipAddress) {
        this.postId = postId;
        this.ipAddress = ipAddress;
    }

    public Long getPostId() {
        return postId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ViewId viewId = (ViewId) o;
        return Objects.equals(postId, viewId.postId) &&
                Objects.equals(ipAddress, viewId.ipAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, ipAddress);
    }

}
