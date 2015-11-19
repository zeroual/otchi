package com.zeros.domain.kitchen.models;

/**
 * Created by MJR2 on 11/19/2015.
 */
@Entity
@Table( name = "INSTRUCTION" )
public class Instruction {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( name = "CONTENT" )
    private String content;

}
