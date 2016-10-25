package com.otchi.domain.events;

import com.otchi.domain.social.models.Post;
import com.otchi.domain.users.models.User;

public class PostNotificationEvent implements Event {

	private final Post post;
	private final String likerUsername;
	private final User user;

	public PostNotificationEvent(final Post post, final User user,
			final String likerUsername) {
		this.post = post;
		this.likerUsername = likerUsername;
		this.user = user;
	}

	public Post getPost() {
		return post;
	}

	public String getLikerUsername() {
		return likerUsername;
	}

	public User getUser() {
		return user;
	}

}
