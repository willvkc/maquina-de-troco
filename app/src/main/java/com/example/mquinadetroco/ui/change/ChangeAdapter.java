package com.example.mquinadetroco.ui.change;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mquinadetroco.R;
import com.example.mquinadetroco.data.model.ItemCoin;

import java.util.List;


public class ChangeAdapter extends RecyclerView.Adapter<ChangeAdapter.ListViewHolder> {

    private List<ItemCoin> resultsItemList;

    public ChangeAdapter(List<ItemCoin> resultsItemList) {
        this.resultsItemList = resultsItemList;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_change, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListViewHolder viewHolder, final int position) {
        ItemCoin itemCoin = resultsItemList.get(position);
        viewHolder.coinTextView.setText(itemCoin.getValueFormat());
        viewHolder.amountTextView.setText("qtd: " + itemCoin.getAmount());
    }

    @Override
    public int getItemCount() {
        return resultsItemList.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView coinTextView;
        private TextView amountTextView;

        ListViewHolder(View view) {
            super(view);
            coinTextView = view.findViewById(R.id.coinTextView);
            amountTextView = view.findViewById(R.id.amountTextView);
        }
    }
}



