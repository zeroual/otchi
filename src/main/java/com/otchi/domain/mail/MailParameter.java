package com.otchi.domain.mail;

import com.google.common.base.Objects;
import com.otchi.domain.users.models.User;

public class MailParameter {
	private final User author;
	private final User liker;
	private final String summary;
	private final String postUrl;

	public MailParameter(User author, User liker, String summary, String postUrl) {
		this.author = author;
		this.liker = liker;
		this.summary = summary;
		this.postUrl = postUrl;
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

	public String getPostUrl() {
		return postUrl;
	}

	@Override
	public String toString() {
		return "MailParameter [author=" + author + ", liker=" + liker + ", summary=" + summary + ", postUrl=" + postUrl
				+ "]";
	}
    @Override
    public int hashCode() {
        return Objects.hashCode(getClass(), author, liker, summary, postUrl);
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
                && Objects.equal(this.postUrl, that.postUrl)
                && Objects.equal(this.summary, that.summary);
        // @formatter:on
    }

	
	
	
}