package com.example.servlety;

public class Car {
    String brand = "";
    int createYear = 0;
    int fuel = 0;
    String type = "";

    public Car(String brand, int createYear, int fuel, String type) {
        this.brand = brand;
        this.createYear = createYear;
        this.fuel = fuel;
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public int getCreateYear() {
        return createYear;
    }

    public int getFuel() {
        return fuel;
    }

    public String getType(){
        return this.type;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", createYear=" + createYear +
                ", fuel=" + fuel +
                '}';
    }
}
