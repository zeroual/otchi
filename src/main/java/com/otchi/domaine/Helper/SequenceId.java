package com.otchi.domaine.Helper;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by MJR2 on 2/18/2016.
 */
@Document(collection = "sequence")
public class SequenceId {
    @Id
    private String id;

    private Long seq;

    public String getId() {
        return id;
    }

    public Long getSeq() {
        return seq;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    @Override
    public String toString() {
        return "SequenceId{" +
                "id='" + id + '\'' +
                ", seq=" + seq +
                '}';
    }
}
