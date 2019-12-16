package com.example.finalyearapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearapplication.R;
import com.example.finalyearapplication.models.ItemEntity;

import java.util.List;

public class ItemProductAdapter extends RecyclerView.Adapter<ItemProductAdapter.Holder> {

    private Context context;
    private List<ItemEntity> listItem;

    public ItemProductAdapter(Context context, List<ItemEntity> listItem) {
        this.context = context;
        this.listItem = listItem;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        ItemEntity item = listItem.get(position);
        holder.tvProductName.setText(item.getName());
        holder.tvProductPrice.setText("Rs." + item.getPrice());
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView tvProductName, tvProductPrice;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
        }
    }
}
