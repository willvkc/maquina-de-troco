package com.example.mquinadetroco.ui.list;

import android.content.Context;
import android.database.Cursor;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mquinadetroco.data.db.DbConfig;
import com.example.mquinadetroco.data.db.DbGateway;
import com.example.mquinadetroco.data.model.ItemCoin;

import java.util.ArrayList;
import java.util.List;

public class ListViewModel extends ViewModel {
    MutableLiveData<List<ItemCoin>> listMutableLiveData = new MutableLiveData<>();

    void getList(Context context) {
        DbGateway dbGateway = DbGateway.getInstance(context);

        List<ItemCoin> itemCoinList = new ArrayList<>();

        Cursor cursor = dbGateway.getDatabase().rawQuery("SELECT * FROM " + DbConfig.TABLE_NAME, null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(DbConfig.ID_NAME));
            int amount = cursor.getInt(cursor.getColumnIndex(DbConfig.AMOUNT_NAME));
            Double value = cursor.getDouble(cursor.getColumnIndex(DbConfig.VALUE_NAME));
            itemCoinList.add(new ItemCoin(id, value, amount));
        }
        cursor.close();
        listMutableLiveData.setValue(itemCoinList);
    }

}
