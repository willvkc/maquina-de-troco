package com.example.mquinadetroco.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mquinadetroco.R;
import com.example.mquinadetroco.data.model.ItemCoin;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private List<ItemCoin> resultsItemList;

    public ListAdapter(List<ItemCoin> resultsItemList) {
        this.resultsItemList = resultsItemList;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        ListViewHolder viewHolder = new ListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ListViewHolder viewHolder, final int position) {
        ItemCoin itemCoin = resultsItemList.get(position);
        viewHolder.coinTextView.setText(itemCoin.getValueFormat());
        viewHolder.amountTextView.setText("qtd: " + itemCoin.getAmount());
        viewHolder.totalTextView.setText(itemCoin.getTotal());
    }

    @Override
    public int getItemCount() {
        return resultsItemList.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        private TextView coinTextView;
        private TextView totalTextView;
        private TextView amountTextView;

        ListViewHolder(View view) {
            super(view);

            coinTextView = view.findViewById(R.id.coinTextView);
            totalTextView = view.findViewById(R.id.totalTextView);
            amountTextView = view.findViewById(R.id.amountTextView);
        }
    }
}



