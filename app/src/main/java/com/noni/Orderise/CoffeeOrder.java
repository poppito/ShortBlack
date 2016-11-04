package com.noni.Orderise;

public class CoffeeOrder {

    private String milkChoice;
    private String orderSize;
    private String coffeeType;
    private String additiveCoice;
    private String coffeeStrength;
    private String specialOrder;

    public CoffeeOrder() {
    }

    public CoffeeOrder(String milkChoice, String orderSize, String coffeeType, String coffeeStrength) {

        this.milkChoice = milkChoice;
        this.orderSize = orderSize;
        this.coffeeType = coffeeType;
        this.coffeeStrength = coffeeStrength;
    }

    public String getMilkChoice() {
        return milkChoice;
    }

    public void setMilkChoice(String milkChoice) {
        this.milkChoice = milkChoice;
    }

    public String getCoffeeType() {
        return coffeeType;
    }

    public void setCoffeeType(String coffeeType) {
        this.coffeeType = coffeeType;
    }

    public String getCoffeeStrength() {
        return coffeeStrength;
    }

    public void setCoffeeStrength(String coffeeStrength) {
        this.coffeeStrength = coffeeStrength;
    }

    public String getSpecialOrder() {
        return specialOrder;
    }

    public void setSpecialOrder(String specialOrder) {
        this.specialOrder = specialOrder;
    }

    public String getAdditiveChoice() {
        return additiveCoice;
    }

    public void setAdditiveChoice(String additiveCoice) {
        this.additiveCoice = additiveCoice;
    }

    public String getOrderSize() {
        return orderSize;
    }

    public void setOrderSize(String orderSize) {
        this.orderSize = orderSize;
    }

    public boolean allValuesValidated() {
        if ((milkChoice != null) && (coffeeType != null) && (coffeeStrength != null) && (additiveCoice != null) && (orderSize != null)) {
            return true;
        }
        else {
            return false;
        }
    }

}
