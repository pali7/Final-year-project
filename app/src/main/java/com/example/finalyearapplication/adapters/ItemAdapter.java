package com.example.finalyearapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalyearapplication.R;
import com.example.finalyearapplication.models.ItemEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.Holder> {

    private Context context;
    private List<ItemEntity> listItem;

    public ItemAdapter(Context context, List<ItemEntity> listItem) {
        this.context = context;
        this.listItem = listItem;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_cloth, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        ItemEntity item = listItem.get(position);
        holder.tvTitle.setText(item.getName());
        holder.tvPrice.setText("Rs." + item.getPrice());
        holder.tvDesc.setText(item.getDescription());
        Glide.with(context).load(item.getImage()).into(holder.imgProduct);
        holder.imgDelete.setOnClickListener(v -> {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_delete, null, false);
            AlertDialog dialog = new AlertDialog.Builder(context).create();
            dialog.setView(view);
            view.findViewById(R.id.tvNo).setOnClickListener(vv -> dialog.dismiss());
            view.findViewById(R.id.tvYes).setOnClickListener(vv -> {
                FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("cart").child(item.getKey()).removeValue();
                removeItem(item);
                notifyDataSetChanged();
                dialog.dismiss();
            });
            dialog.show();

        });
    }

    private void removeItem(ItemEntity item) {
        for (ItemEntity entity : listItem) {
            if (item.getName().equals(entity.getName())) {
                listItem.remove(item);
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView tvTitle, tvPrice, tvDesc;
        private ImageView imgProduct, imgDelete;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }
}
