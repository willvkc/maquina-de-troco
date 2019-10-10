package com.example.mquinadetroco.ui.change;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mquinadetroco.R;
import com.example.mquinadetroco.data.model.Response;

import java.util.Objects;

public class ChangeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        final ChangeViewModel changeViewModel = ViewModelProviders.of(this, null).get(ChangeViewModel.class);
        changeViewModel.create(this);

        //ActionBar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Gerar Troco");

        //findViewById
        final EditText countEditText = findViewById(R.id.countEditText);
        final EditText amountEditText = findViewById(R.id.amountEditText);
        final RecyclerView listRecyclerView = findViewById(R.id.listRecyclerView);
        final TextView textView = findViewById(R.id.textView);
        final TextView totalTextView = findViewById(R.id.totalTextView);

        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countEditText.getText().toString().isEmpty() || countEditText.getText().toString().equals(".")) {
                    countEditText.requestFocus();
                    showMessage("Valor da conta deve ser preenchido.");
                    return;
                }
                if (amountEditText.getText().toString().isEmpty() || amountEditText.getText().toString().equals(".")) {
                    amountEditText.requestFocus();
                    showMessage("Valor a ser pago deve ser preenchido.");
                    return;
                }
                if (Double.parseDouble(countEditText.getText().toString()) >= Double.parseDouble(amountEditText.getText().toString())) {
                    amountEditText.requestFocus();
                    showMessage("Valor da conta não pode ser maior ou igual do que o pagamento.");
                    return;
                } else {

                    changeViewModel.getChange(Double.parseDouble(countEditText.getText().toString()), Double.parseDouble(amountEditText.getText().toString()));
                }
            }
        });


        changeViewModel.responseLiveData.observe(this, new Observer<Response>() {
            @Override
            public void onChanged(Response response) {
                if (response != null) {
                    showMessage(response.getMessage());

                    totalTextView.setText("");

                    if (!response.isError()) {
                        textView.setVisibility(View.VISIBLE);
                        totalTextView.setVisibility(View.VISIBLE);
                        listRecyclerView.setVisibility(View.VISIBLE);
                        totalTextView.setText(response.getTotal());
                        ChangeAdapter listAdapter = new ChangeAdapter(response.getItemCoins());
                        listRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        listRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        listRecyclerView.setAdapter(listAdapter);
                    } else {
                        textView.setVisibility(View.INVISIBLE);
                        listRecyclerView.setVisibility(View.INVISIBLE);
                        totalTextView.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });


    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}
