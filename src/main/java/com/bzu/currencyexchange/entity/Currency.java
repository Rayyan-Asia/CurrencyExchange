package com.bzu.currencyexchange.entity;


import jakarta.persistence.*;

@Entity
@Access(AccessType.FIELD)
public class Currency {

    public Currency(String code, double value) {
        this.code = code;
        this.value = value;
    }

    public Currency(){

    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(length = 3, unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private double value;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
