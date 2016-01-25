package com.otchi.domaine.kitchen.models;


import javax.persistence.*;
@Entity
@Table( name = "INSTRUCTION" )
public class Instruction {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( name = "CONTENT" )
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
