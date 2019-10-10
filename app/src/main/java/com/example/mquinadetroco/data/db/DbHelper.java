package com.example.mquinadetroco.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mquinadetroco.data.model.ItemCoin;

import java.util.ArrayList;
import java.util.List;



public class DbHelper extends SQLiteOpenHelper {



    private final String CREATE_TABLE = "CREATE TABLE " + DbConfig.TABLE_NAME + " ("
            + DbConfig.ID_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DbConfig.VALUE_NAME + " DOUBLE NOT NULL,"
            + DbConfig.AMOUNT_NAME + " INT);";

    public DbHelper(Context context) {
        super(context, DbConfig.DATABASE_NAME, null, DbConfig.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        configDb(db);
    }

    private void configDb(SQLiteDatabase db) {

        List<ItemCoin> coinArrayList = new ArrayList<>();

        coinArrayList.add(new ItemCoin(0, 100, 0));
        coinArrayList.add(new ItemCoin(0, 50, 0));
        coinArrayList.add(new ItemCoin(0, 20, 0));
        coinArrayList.add(new ItemCoin(0, 10, 0));
        coinArrayList.add(new ItemCoin(0, 5, 0));
        coinArrayList.add(new ItemCoin(0, 2, 0));
        coinArrayList.add(new ItemCoin(0, 1.0, 0));
        coinArrayList.add(new ItemCoin(0, 0.50, 0));
        coinArrayList.add(new ItemCoin(0, 0.25, 0));
        coinArrayList.add(new ItemCoin(0, 0.10, 0));
        coinArrayList.add(new ItemCoin(0, 0.05, 0));

        for (ItemCoin itemCoin : coinArrayList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DbConfig.VALUE_NAME, itemCoin.getValue());
            contentValues.put(DbConfig.AMOUNT_NAME, itemCoin.getAmount());
            db.insert(DbConfig.TABLE_NAME, null, contentValues);
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);
    }

}
