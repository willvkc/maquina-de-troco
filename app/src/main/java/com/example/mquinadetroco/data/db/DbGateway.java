package com.example.mquinadetroco.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DbGateway {
    private static DbGateway dbGateway;
    private SQLiteDatabase db;
    private DbHelper helper;

    public DbGateway(Context context) {
        helper = new DbHelper(context);
        db = helper.getWritableDatabase();
    }

    public static DbGateway getInstance(Context context) {
        if (dbGateway == null)
            dbGateway = new DbGateway(context);
        return dbGateway;
    }

    public SQLiteDatabase getDatabase() {
        return this.db;
    }

    public DbHelper getHelper() {
        return this.helper;
    }
}
