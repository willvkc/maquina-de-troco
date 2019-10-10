package com.example.mquinadetroco.data.model;

import java.text.NumberFormat;
import java.util.List;

public class Response {

    private String message;
    private boolean error;
    //values of change
    private List<ItemCoin> itemCoins;
    private Double total;

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ItemCoin> getItemCoins() {
        return itemCoins;
    }

    public void setItemCoins(List<ItemCoin> itemCoins) {
        this.itemCoins = itemCoins;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Response(String message, boolean error) {
        this.message = message;
        this.error = error;
    }

    public String getTotal() {

        return NumberFormat.getCurrencyInstance().format(total);
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
