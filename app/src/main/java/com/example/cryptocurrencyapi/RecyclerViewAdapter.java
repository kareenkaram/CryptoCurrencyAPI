package com.example.cryptocurrencyapi;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Crypto.Market> marketList;

    public RecyclerViewAdapter() {
        marketList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        Crypto.Market market = marketList.get(position);
        holder.txtCoin.setText(market.coinName);
        holder.txtMarket.setText(market.market);
        holder.txtPrice.setText("$" + String.format("%.2f", Double.parseDouble(market.price)));
        if (market.coinName.equalsIgnoreCase("eth")) {
            holder.cardView.setCardBackgroundColor(Color.GRAY);
        } else {
            holder.cardView.setCardBackgroundColor(Color.GREEN);
        }
    }

    @Override
    public int getItemCount() {
        return marketList.size();
    }

    public void setData(List<Crypto.Market> data) {
        this.marketList.addAll(data);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtCoin;
        public TextView txtMarket;
        public TextView txtPrice;
        public CardView cardView;

        public ViewHolder(View view) {
            super(view);

            txtCoin = view.findViewById(R.id.txtCoin);
            txtMarket = view.findViewById(R.id.txtMarket);
            txtPrice = view.findViewById(R.id.txtPrice);
            cardView = view.findViewById(R.id.cardView);
        }
    }
}

