package com.otchi.domaine.kitchen.models;


import org.springframework.data.annotation.Id;


public class Instruction {

    @Id
    private Long id;

    private String content;

    public Instruction() {
    }

    public Instruction(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public Long getId() {
        return id;
    }
}
