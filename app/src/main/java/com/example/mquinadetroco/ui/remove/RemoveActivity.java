package com.example.mquinadetroco.ui.remove;

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

public class RemoveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);

        //ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Remover dinheiro");

        final EditText amountEditText = findViewById(R.id.amountEditText);
        final Spinner spinnerCoin = findViewById(R.id.spinnerCoin);

        final RemoveViewModel removeViewModel = ViewModelProviders.of(this, null).get(RemoveViewModel.class);
        removeViewModel.create(this);

        removeViewModel.listMutableLiveData.observe(this, new Observer<List<ItemCoin>>() {
            @Override
            public void onChanged(List<ItemCoin> itemCoins) {
                if (itemCoins != null) {

                    if (itemCoins.size() == 0) {
                        showMessage("Seu caixa está vazio, nenhuma moeda pode ser removida!");
                        finish();
                        return;
                    }

                    final RemoveSpinnerAdapter removeSpinnerAdapter = new RemoveSpinnerAdapter(getApplicationContext(), R.layout.item_spinner, itemCoins);
                    spinnerCoin.setAdapter(removeSpinnerAdapter);

                    findViewById(R.id.removeButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (amountEditText.getText().toString().isEmpty()) {
                                showMessage("Quantidade deve ser preenchida");
                            } else {
                                int id = (int) removeSpinnerAdapter.getItemId(spinnerCoin.getSelectedItemPosition());
                                int amount = Integer.parseInt(amountEditText.getText().toString());
                                removeViewModel.removeCoin(new ItemCoin(id, 0.0, amount));
                            }
                        }
                    });

                }
            }
        });

        removeViewModel.responseLiveData.observe(this, new Observer<Response>() {
            @Override
            public void onChanged(Response response) {
                if (response != null) {
                    showMessage(response.getMessage());
                    if (!response.isError()) finish();
                }

            }
        });


        removeViewModel.getList();
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
