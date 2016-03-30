package com.otchi.domain.social.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class PostContent implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

    public Long getId() {
        return id;
    }
}
