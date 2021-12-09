package com.carrental.carrental.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private int carStatus;
    @NotNull
    private String brand;
    @NotNull
    private String model;
    @NotNull
    private int power;
    @NotNull
    private double price;

    public Car(String brand, String model, int power, double price) {
        this.brand = brand;
        this.model = model;
        this.power = power;
        this.price = price;
    }
}
