package com.example.mquinadetroco.ui.list;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mquinadetroco.R;
import com.example.mquinadetroco.data.model.ItemCoin;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    private ListViewModel listViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Caixa");

        final RecyclerView listRecyclerView = findViewById(R.id.listRecyclerView);

        listViewModel = ViewModelProviders.of(this, null).get(ListViewModel.class);
        listViewModel.create(this);
        listViewModel.listMutableLiveData.observe(this, new Observer<List<ItemCoin>>() {
            @Override
            public void onChanged(List<ItemCoin> itemCoins) {
                if (itemCoins != null){
                    ListAdapter listAdapter = new ListAdapter(itemCoins);
                    listRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    listRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    listRecyclerView.setAdapter(listAdapter);
                }
            }
        });

        listViewModel.getList();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}
