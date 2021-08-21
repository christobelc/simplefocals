package com.github.simplefocals.entity;

//import javax.persistence.*;
//
//@Entity
//@Table(name = "quantity")
public class Quantity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

//    @Column(name = "value", nullable = true)
    private Integer value;

    public Quantity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
