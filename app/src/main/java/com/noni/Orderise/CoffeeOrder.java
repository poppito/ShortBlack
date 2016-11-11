package com.noni.Orderise;

import android.content.Context;

import java.io.Serializable;

public class CoffeeOrder implements Serializable {

    private String milkChoice;
    private String orderSize;
    private String coffeeType;
    private String additiveChoice;
    private String coffeeStrength;
    private String specialOrder;
    private String orderName;

    public CoffeeOrder() {
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
        return additiveChoice;
    }

    public void setAdditiveChoice(String additiveCoice) {
        this.additiveChoice = additiveCoice;
    }

    public String getOrderSize() {
        return orderSize;
    }

    public void setOrderSize(String orderSize) {
        this.orderSize = orderSize;
    }

    public boolean allValuesValidated() {
        if ((milkChoice != null) && (coffeeType != null) && (coffeeStrength != null) && (additiveChoice != null) && (orderSize != null)) {
            return true;
        }
        else {
            return false;
        }
    }

    public String displayOrder() {
        if (allValuesValidated()) {
            return "A " + orderSize + ", " + coffeeStrength + " " + coffeeType + " with " + milkChoice + " and " + additiveChoice + " for " + orderName;
        }
        return "";
    }

    public String checkForOrderCompletion(Context c) {
        if (allValuesValidated()) {
            return c.getResources().getString(R.string.orderisGood);
        }
        else {
            if (milkChoice == null) {
                return c.getResources().getString(R.string.milkMissing);
            } else if (additiveChoice == null) {
                return c.getResources().getString(R.string.additiveMissing);
            } else if (coffeeType == null) {
                return c.getResources().getString(R.string.coffeeTypeMissing);
            } else if (orderSize == null) {
                return c.getResources().getString(R.string.orderSizeMissing);
            } else if (coffeeStrength == null) {
                return c.getResources().getString(R.string.coffeeStrengthMissing);
            }
            else {
                return "";
            }
        }
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderName() {
        return orderName;
    }
}