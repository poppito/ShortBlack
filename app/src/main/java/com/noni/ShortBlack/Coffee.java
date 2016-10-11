package com.noni.ShortBlack;

public class Coffee<T> {

    private String coffeeType, additiveChoice, orderSize, milkChoice;


    private void setMilkChoice(String milkChoice) {

        this.milkChoice = milkChoice;
    }

    private String getMilkChoice() {
        return milkChoice;
    }

    private void setOrderSize(String orderSize) {
        this.orderSize = orderSize;
    }


    private String getOrderSize() {
        return orderSize;
    }


    private String getCoffeeType() {
        return coffeeType;
    }

    private void setCoffeeType(String coffeeType) {
        this.coffeeType = coffeeType;
    }

    private String getAdditiveChoice() {
        return additiveChoice;
    }

    private void setAdditiveChoice(String additiveChoice) {
        this.additiveChoice = additiveChoice;
    }

}
