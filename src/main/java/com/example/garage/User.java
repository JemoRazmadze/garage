package com.example.garage;

import com.example.garage.entity.Car;
import lombok.Getter;
import lombok.Setter;

class User {
    private final String userName;
    @Getter
    @Setter
    private int age;

    @Getter
    @Setter
    private  String email;

    @Getter
    private Car ownedCar;

    public User(String name, int age, String email) {
        this.userName = name;
        this.age = age;
        this.email = email;
    }

    public void buyCar(Car car) {
        if (this.ownedCar == null) {
            this.ownedCar = car;
            System.out.println(userName + " just bought a " + car.getModel() + " (" + car.getColor() + ")");
        } else {
            System.out.println(userName + " already owns a car: " + ownedCar.getModel());
        }
    }

}
