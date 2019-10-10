package com.example.mquinadetroco.ui.change;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mquinadetroco.data.db.DbConfig;
import com.example.mquinadetroco.data.db.DbGateway;
import com.example.mquinadetroco.data.model.ItemCoin;
import com.example.mquinadetroco.data.model.Response;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ChangeViewModel extends ViewModel {
    MutableLiveData<Response> responseLiveData = new MutableLiveData<>();
    private DbGateway dbGateway;

    void create(@NonNull Context context) {
        dbGateway = DbGateway.getInstance(context);
    }

    private List<ItemCoin> getList() {
        List<ItemCoin> itemCoinList = new ArrayList<>();
        Cursor cursor = dbGateway.getDatabase().rawQuery("SELECT * FROM " + DbConfig.TABLE_NAME + " ORDER BY " + DbConfig.VALUE_NAME + " DESC", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DbConfig.ID_NAME));
            int amount = cursor.getInt(cursor.getColumnIndex(DbConfig.AMOUNT_NAME));
            double value = cursor.getDouble(cursor.getColumnIndex(DbConfig.VALUE_NAME));
            if (amount >= 1) itemCoinList.add(new ItemCoin(id, value, amount));
        }
        cursor.close();
        return itemCoinList;
    }

    void getChange(double count, double pay) {
        List<ItemCoin> listCoinsSelected = new ArrayList<>();
        DecimalFormat moneyFormat = new DecimalFormat("#.##");
        List<ItemCoin> listCoins = getList();
        double change = Double.parseDouble(moneyFormat.format(pay - count).replace(",", "."));
        boolean accept;

        for (int i = 0; i < listCoins.size(); i++) {
            ItemCoin itemCoin = listCoins.get(i);
            double value = itemCoin.getValue();
            int amount = (int) (change / value);
            double remnant = change % value;
            remnant = Double.valueOf(moneyFormat.format(remnant).replace(",", "."));

            if (value <= change) {

                //If the required amount of forum coins is needed, it will not fetch any more available coins.
                if (amount == 1) {
                    listCoinsSelected.add(new ItemCoin(itemCoin.getId(), value, amount));
                    change = Double.valueOf(moneyFormat.format(remnant).replace(",", "."));
                }
                if (amount > 1) {
                    //Compare if you have the available amount of boxed coins, if not, go to next coin.
                    if (amount > itemCoin.getAmount()) {
                        remnant = Double.valueOf(moneyFormat.format(change - ((amount - itemCoin.getAmount()) * value)).replace(",", "."));
                        amount = itemCoin.getAmount();
                    }
                    change = Double.valueOf(moneyFormat.format(remnant).replace(",", "."));
                    listCoinsSelected.add(new ItemCoin(itemCoin.getId(), value, amount));
                }
            }
        }
        accept = change == 0;
        if (accept) {
            removeCoins(listCoinsSelected, pay - count);
        } else {
            Response response = new Response("Não possui dinheiro necessário para gerar troco.", true);
            responseLiveData.setValue(response);
        }
    }

    private void removeCoins(List<ItemCoin> itemCoinList, Double sum) {
        for (ItemCoin itemCoin : itemCoinList) {
            Cursor cursor = dbGateway.getDatabase().rawQuery("SELECT * FROM " + DbConfig.TABLE_NAME + " WHERE " + DbConfig.ID_NAME + "= ?", new String[]{itemCoin.getId().toString()});
            cursor.moveToFirst();
            int amount = cursor.getInt(cursor.getColumnIndex(DbConfig.AMOUNT_NAME));
            amount = amount - itemCoin.getAmount();
            cursor.close();
            ContentValues values = new ContentValues();
            values.put(DbConfig.AMOUNT_NAME, amount);
            dbGateway.getDatabase().update(DbConfig.TABLE_NAME, values, DbConfig.ID_NAME + "=?", new String[]{Integer.toString(itemCoin.getId())});
        }

        Response response = new Response("Troco gerado com sucesso.", false);
        response.setItemCoins(itemCoinList);
        response.setTotal(sum);
        responseLiveData.setValue(response);
    }


}
