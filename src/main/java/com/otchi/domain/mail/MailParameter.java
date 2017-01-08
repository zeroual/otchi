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

	
	@Override
	public String toString() {
		return "MailParameter [author=" + author + ", liker=" + liker + ", summary=" + summary + ", postId=" + postId
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((liker == null) ? 0 : liker.hashCode());
		result = prime * result + ((postId == null) ? 0 : postId.hashCode());
		result = prime * result + ((summary == null) ? 0 : summary.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MailParameter other = (MailParameter) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (liker == null) {
			if (other.liker != null)
				return false;
		} else if (!liker.equals(other.liker))
			return false;
		if (postId == null) {
			if (other.postId != null)
				return false;
		} else if (!postId.equals(other.postId))
			return false;
		if (summary == null) {
			if (other.summary != null)
				return false;
		} else if (!summary.equals(other.summary))
			return false;
		return true;
	}
	
	
	
}