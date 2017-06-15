package com.otchi.domain.analytics;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "POST_VIEW")
public class PostView {

    @EmbeddedId
    private ViewId view;


    private PostView() {
    }

    public PostView(Long postId, String ipAddress) {
        this.view = new ViewId(postId, ipAddress);
    }

    public Long getPostId(){
        return view.getPostId();
    }

}
