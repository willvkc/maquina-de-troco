package com.example.mquinadetroco.data;

import android.content.Context;

import com.example.mquinadetroco.R;

public class teste {

    private int id;
    private double value;
    private int amount;

    public teste(int id, double value, int amount) {
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

    public String getType(Context context) {
        if (getAmount() > 1) {
            return context.getString(R.string.bank_note_name);
        } else {
            return context.getString(R.string.coin_name);
        }
    }
}
