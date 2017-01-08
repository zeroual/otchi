package com.otchi.domain.mail;

import com.otchi.domain.users.models.User;

public class MailParameter {
	private final User author;
	private final User liker;
	private final String summary;
	private final Long postId;

	public MailParameter(User author, User liker, String summary, Long postId) {
		this.author = author;
		this.liker = liker;
		this.summary = summary;
		this.postId = postId;
	}

	public User getAuthor() {
		return author;
	}

	public User getLiker() {
		return liker;
	}

	public String getSummary() {
		return summary;
	}

	public Long getPostId() {
		return postId;
	}
	
	
}