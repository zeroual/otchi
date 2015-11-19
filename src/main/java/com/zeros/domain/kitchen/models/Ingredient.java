package com.zeros.domain.kitchen.models;

/**
 * Created by MJAIFAR on 11/19/2015.
 */
@Entity
@Table(name = "INGREDIENT")
public class Ingredient {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( name = "NAME" )
    private String name;

    @Column( name = "UNIT" )
    private String unit;

    @Column( name = "AMOUNT" )
    private long amount;

    public Ingredient(){

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public long getAmount() {
        return amount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", amount=" + amount +
                '}';
    }
}
