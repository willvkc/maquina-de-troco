package com.example.mquinadetroco.data.model;

import java.text.NumberFormat;

public class ItemCoin {

    private Integer id;
    private double value;
    private Integer amount;

    public ItemCoin(Integer id, double value, Integer amount) {
        this.id = id;
        this.value = value;
        this.amount = amount;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public double getValue() {
        return value;
    }


    public void setValue(double value) {
        this.value = value;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    private String getType() {
        if (getValue() > 1) {
            return "Cédula ";
        } else {
            return "Moeda ";
        }
    }

    public String getValueFormat1() {
        return getType() + NumberFormat.getCurrencyInstance().format(value * amount);
    }

    public String getValueFormat() {
        return getType() + NumberFormat.getCurrencyInstance().format(value);
    }

    public String getTotal() {
        if (amount == 0) return "";
        else return "Total: " + NumberFormat.getCurrencyInstance().format(value * amount);
    }
}
