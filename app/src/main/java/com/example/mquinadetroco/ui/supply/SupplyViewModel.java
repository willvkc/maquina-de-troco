package com.example.mquinadetroco.ui.supply;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mquinadetroco.data.db.DbConfig;
import com.example.mquinadetroco.data.db.DbGateway;
import com.example.mquinadetroco.data.model.ItemCoin;
import com.example.mquinadetroco.data.model.Response;

import java.util.ArrayList;
import java.util.List;

public class SupplyViewModel extends ViewModel {

    MutableLiveData<List<ItemCoin>> listMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Response> responseLiveData = new MutableLiveData<>();
    private DbGateway dbGateway;

    void create(Context context) {
        dbGateway = DbGateway.getInstance(context);
    }

    void getList() {
        List<ItemCoin> itemCoinList = new ArrayList<>();
        Cursor cursor = dbGateway.getDatabase().rawQuery("SELECT * FROM " + DbConfig.TABLE_NAME + " ORDER BY " + DbConfig.VALUE_NAME + " DESC", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DbConfig.ID_NAME));
            int amount = cursor.getInt(cursor.getColumnIndex(DbConfig.AMOUNT_NAME));
            Double value = cursor.getDouble(cursor.getColumnIndex(DbConfig.VALUE_NAME));
            itemCoinList.add(new ItemCoin(id, value, amount));
        }
        cursor.close();
        listMutableLiveData.setValue(itemCoinList);

    }

    void updateCoin(ItemCoin itemCoin) {

        if (itemCoin.getAmount() == null) {
            responseLiveData.setValue(new Response("Quantidade deve ser preenchido", true));
            return;
        }
        if (itemCoin.getAmount() == 0) {
            responseLiveData.setValue(new Response("Quantidade a ser adicionado não pode ser zero.", true));
            return;
        }

        Cursor cursor = dbGateway.getDatabase().rawQuery("SELECT * FROM " + DbConfig.TABLE_NAME + " WHERE " + DbConfig.ID_NAME + "= ?", new String[]{itemCoin.getId().toString()});
        cursor.moveToFirst();
        int amount = cursor.getInt(cursor.getColumnIndex(DbConfig.AMOUNT_NAME));
        cursor.close();

        ContentValues values = new ContentValues();
        values.put(DbConfig.AMOUNT_NAME, amount + itemCoin.getAmount());
        dbGateway.getDatabase().update(DbConfig.TABLE_NAME, values, DbConfig.ID_NAME + "=?", new String[]{Integer.toString(itemCoin.getId())});
        responseLiveData.setValue(new Response("Quantidade adicionada com sucesso.", false));

    }


}
