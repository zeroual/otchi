package com.otchi.domain.social.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "STORY")
public class Story extends PostContent {

    @Column(name = "CONTENT")
    private String content;


    private Story() {

    }

    public Story(String content) {

        this.content = content;
    }

    public String content() {
        return content;
    }

	@Override
	public String getSummary() {
		return content.subSequence(0, 20).toString();
	}

}
