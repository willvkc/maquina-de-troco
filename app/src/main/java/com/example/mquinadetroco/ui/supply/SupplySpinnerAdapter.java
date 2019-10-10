package com.example.mquinadetroco.ui.supply;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.mquinadetroco.R;
import com.example.mquinadetroco.data.model.ItemCoin;

import java.util.List;


public class SupplySpinnerAdapter extends ArrayAdapter<ItemCoin> {

    private final Context context;
    private final List<ItemCoin> itemCoinList;

    public SupplySpinnerAdapter(Context context, int textViewResourceId, List<ItemCoin> itemCoinList) {
        super(context, textViewResourceId, itemCoinList);
        this.itemCoinList = itemCoinList;
        this.context = context;

    }

    @Override
    public int getCount() {
        return itemCoinList.size();
    }

    @Override
    public ItemCoin getItem(int position) {
        return itemCoinList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itemCoinList.get(position).getId();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setText(itemCoinList.get(position).getValueFormat());
        return label;
    }

    @Override
    public View getDropDownView(final int position, View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_spinner_drop, parent, false);
        TextView nameTextView = view.findViewById(R.id.nameTextView);
        nameTextView.setText(itemCoinList.get(position).getValueFormat());
        return view;
    }
}
