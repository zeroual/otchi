package com.otchi.domaine.kitchen.models;

import javax.persistence.*;

@Entity
@Table(name = "INGREDIENT")
public class Ingredient {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( name = "NAME" )
    private String name;

    @Column(name = "QUANTITY")
    private Double quantity;

    @Column(name = "UNIT")
    private String unit;

    public Ingredient() {
    }

    public Ingredient(String name, Double quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public Double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }
}
