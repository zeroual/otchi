package com.otchi.domain.mail;

import com.edelia.oauth2.authorizationserver.userdetails.ExternalId;
import com.google.common.base.Objects;
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
        return Objects.hashCode(getClass(), author, liker, summary, postId);
    }

    @Override
    public boolean equals(final Object object) {
        if (object == this) {
            return true;
        } else if (object == null || !getClass().equals(object.getClass())) {
            return false;
        }
        // @formatter:off
        MailParameter that = (MailParameter) object;
        return Objects.equal(this.author, that.author)
                && Objects.equal(this.liker, that.liker)
                && Objects.equal(this.postId, that.postId)
                && Objects.equal(this.summary, that.summary);
        // @formatter:on
    }

	
	
	
}