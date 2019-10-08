package com.example.mquinadetroco.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mquinadetroco.R;
import com.example.mquinadetroco.data.db.DbGateway;
import com.example.mquinadetroco.ui.list.ListActivity;
import com.example.mquinadetroco.ui.list.ListViewModel;


public class HomeActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbGateway dbGateway = DbGateway.getInstance(this);

        findViewById(R.id.listButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ListActivity.class));
            }
        });


    }
}
