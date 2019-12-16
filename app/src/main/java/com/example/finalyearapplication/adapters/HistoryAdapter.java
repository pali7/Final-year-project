package com.example.finalyearapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalyearapplication.R;
import com.example.finalyearapplication.activities.HistoryDetailsActivity;
import com.example.finalyearapplication.models.HistoryEntity;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.Holder> {
    private Context context;
    private List<HistoryEntity> historyList;

    public HistoryAdapter(Context context, List<HistoryEntity> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        HistoryEntity history = historyList.get(position);
        Glide.with(context).load(history.getImage()).into(holder.imgProduct);
        holder.tvTitle.setText(history.getTitle());
        holder.tvTotal.setText("Rs." + history.getTotalPrice());
        holder.tvDate.setText("Date: " + history.getDate());
        holder.tvQuantity.setText("Quantity: " + history.getQty() + " items");

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, HistoryDetailsActivity.class);
            intent.putExtra("list", history.getList());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView imgProduct;
        private TextView tvTitle, tvTotal, tvDate, tvQuantity;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
        }
    }
}
