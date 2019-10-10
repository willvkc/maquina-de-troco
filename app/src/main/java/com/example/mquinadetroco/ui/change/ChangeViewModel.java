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

    List<ItemCoin> getList() {
        List<ItemCoin> itemCoinList = new ArrayList<>();
        Cursor cursor = dbGateway.getDatabase().rawQuery("SELECT * FROM " + DbConfig.TABLE_NAME + " ORDER BY " + DbConfig.VALUE_NAME + " DESC", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DbConfig.ID_NAME));
            int amount = cursor.getInt(cursor.getColumnIndex(DbConfig.AMOUNT_NAME));
            Double value = cursor.getDouble(cursor.getColumnIndex(DbConfig.VALUE_NAME));
            if (amount >= 1) itemCoinList.add(new ItemCoin(id, value, amount));
        }
        cursor.close();
        return itemCoinList;
    }

    void calculaTroco(double conta, double pago) {
        List<ItemCoin> listCoinsSelected = new ArrayList<>();
        DecimalFormat formato = new DecimalFormat("#.##");
        List<ItemCoin> listCoins = getList();
        double troco = Double.parseDouble(formato.format(pago - conta).replace(",", "."));
        boolean accept = listCoins.size() > 0;

        teste:
        for (int i = 0; i < listCoins.size(); i++) {
            ItemCoin itemCoin = listCoins.get(i);
            double value = itemCoin.getValue();
            int amount = (int) (troco / value);
            double remnant = troco % value;
            remnant = Double.valueOf(formato.format(remnant).replace(",", "."));

            if (value <= troco) {

                if (amount == 1) {
                    listCoinsSelected.add(new ItemCoin(itemCoin.getId(), value, amount));
                    troco = Double.valueOf(formato.format(remnant).replace(",", "."));
                }
                if (amount > 1) {
                    if (amount > itemCoin.getAmount()) {
                        remnant = Double.valueOf(formato.format(troco - ((amount - itemCoin.getAmount()) * value)).replace(",", "."));
                        amount = itemCoin.getAmount();
                    }
                    troco = Double.valueOf(formato.format(remnant).replace(",", "."));
                    listCoinsSelected.add(new ItemCoin(itemCoin.getId(), value, amount));
                }
            }
        }
        //Log.i("Teste", "troco_final " + troco);
        accept = troco == 0;
        if (accept) {
            removeCoins(listCoinsSelected, pago - conta);
        } else {
            Response response = new Response("Não possui moedas necessárias para gerar troco.", true);
            responseLiveData.setValue(response);
        }
    }


    private void removeCoins(List<ItemCoin> itemCoinList, Double sum) {
        for (ItemCoin itemCoin : itemCoinList) {
            Cursor cursor = dbGateway.getDatabase().rawQuery("SELECT * FROM " + DbConfig.TABLE_NAME + " WHERE " + DbConfig.ID_NAME + "= ?", new String[]{itemCoin.getId().toString()});
            cursor.moveToFirst();
            int amount = cursor.getInt(cursor.getColumnIndex(DbConfig.AMOUNT_NAME)) - itemCoin.getAmount();
            cursor.close();
            ContentValues values = new ContentValues();
            values.put(DbConfig.AMOUNT_NAME, amount);
            dbGateway.getDatabase().update(DbConfig.TABLE_NAME, values, DbConfig.ID_NAME + "=?", new String[]{Integer.toString(itemCoin.getId())});
        }

        Response response = new Response("Troco Gerado com sucesso", false);
        response.setItemCoins(itemCoinList);
        response.setTotal(sum);
        responseLiveData.setValue(response);

    }


}
