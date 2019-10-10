package com.example.mquinadetroco.ui.supply;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mquinadetroco.R;
import com.example.mquinadetroco.data.model.ItemCoin;
import com.example.mquinadetroco.data.model.Response;

import java.util.List;
import java.util.Objects;

public class SupplyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supply);

        //ActionBar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Adicionar dinheiro");

        //Find views
        final EditText amountEditText = findViewById(R.id.amountEditText);
        final Spinner spinnerCoin = findViewById(R.id.spinnerCoin);

        final SupplyViewModel supplyViewModel = ViewModelProviders.of(this, null).get(SupplyViewModel.class);
        supplyViewModel.create(this);

        supplyViewModel.listMutableLiveData.observe(this, new Observer<List<ItemCoin>>() {
            @Override
            public void onChanged(List<ItemCoin> itemCoins) {
                if (itemCoins != null) {
                    final SupplySpinnerAdapter supplySpinnerAdapter = new SupplySpinnerAdapter(getApplicationContext(), R.layout.item_spinner, itemCoins);
                    spinnerCoin.setAdapter(supplySpinnerAdapter);

                    findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (amountEditText.getText().toString().isEmpty()) {
                                showMessage("Quantidade deve ser preenchida.");
                            } else {
                                int id = (int) supplySpinnerAdapter.getItemId(spinnerCoin.getSelectedItemPosition());
                                int amount = Integer.parseInt(amountEditText.getText().toString());
                                supplyViewModel.updateCoin(new ItemCoin(id, 0.0, amount));
                            }

                        }
                    });

                }
            }
        });
        supplyViewModel.responseLiveData.observe(this, new Observer<Response>() {
            @Override
            public void onChanged(Response response) {
                if (response != null) {
                    showMessage(response.getMessage());
                    amountEditText.setText("");
                    if (!response.isError()) {
                        supplyViewModel.getList();
                    }
                }
            }
        });


        supplyViewModel.getList();

    }

    void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}
