package com.example.mquinadetroco.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mquinadetroco.R;
import com.example.mquinadetroco.data.db.DbGateway;
import com.example.mquinadetroco.ui.change.ChangeActivity;
import com.example.mquinadetroco.ui.list.ListActivity;
import com.example.mquinadetroco.ui.remove.RemoveActivity;
import com.example.mquinadetroco.ui.supply.SupplyActivity;


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //create database
        DbGateway dbGateway = DbGateway.getInstance(this);

        findViewById(R.id.listButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ListActivity.class));
            }
        });

        findViewById(R.id.supplyButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, SupplyActivity.class));
            }
        });

        findViewById(R.id.removeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, RemoveActivity.class));
            }
        });


        findViewById(R.id.changeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ChangeActivity.class));
            }
        });


    }
}
