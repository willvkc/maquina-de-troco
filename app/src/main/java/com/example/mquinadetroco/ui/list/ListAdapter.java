package com.example.mquinadetroco.ui.list;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mquinadetroco.R;
import com.example.mquinadetroco.data.model.ItemCoin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by willv on 26/10/2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private List<ItemCoin> resultsItemList;

    public ListAdapter(List<ItemCoin> resultsItemList) {
        this.resultsItemList = resultsItemList;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        ListViewHolder viewHolder = new ListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ListViewHolder viewHolder, final int position) {
        ItemCoin itemCoin = resultsItemList.get(position);
        viewHolder.textView.setText(itemCoin.getType() + itemCoin.getValueFormat() + " qtd: " + itemCoin.getAmount());
    }

    @Override
    public int getItemCount() {
        return resultsItemList.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        ListViewHolder(View view) {
            super(view);

            textView = view.findViewById(R.id.textView);
        }
    }
}



