package com.example.mquinadetroco.data.model;

import android.content.Context;

import com.example.mquinadetroco.R;

public class ItemCoin {

    private int id;
    private double value;
    private int amount;

    public ItemCoin(int id, double value, int amount) {
        this.id = id;
        this.value = value;
        this.amount = amount;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getType() {
        if (getValue() > 1) {
            return "Cédula ";
        } else {
            return "Moeda ";
        }
    }

    public String getValueFormat() {
        return "R$" + Double.toString(value).replace(".", ",");
    }
}
