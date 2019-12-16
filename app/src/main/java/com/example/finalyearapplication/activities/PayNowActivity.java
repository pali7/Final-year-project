package com.example.finalyearapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearapplication.R;
import com.example.finalyearapplication.adapters.ItemProductAdapter;
import com.example.finalyearapplication.models.ItemEntity;
import com.example.finalyearapplication.utils.DateUtils;
import com.example.finalyearapplication.utils.ToastUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import khalti.checkOut.api.Config;
import khalti.checkOut.api.OnCheckOutListener;
import khalti.widget.KhaltiButton;

public class PayNowActivity extends AppCompatActivity {

    private List<ItemEntity> listItems;
    private ItemProductAdapter adapter;
    private RecyclerView recyclerView;
    private KhaltiButton khaltiButton;
    private TextView tvTotal;
    private int total;
    private FirebaseAuth auth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_now);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference().child("users");

        listItems = (List<ItemEntity>) getIntent().getSerializableExtra("list");
        adapter = new ItemProductAdapter(this, listItems);

        tvTotal = findViewById(R.id.tvTotal);
        khaltiButton = findViewById(R.id.khalti_button);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(adapter);

        total = getTotalPrice();
        tvTotal.setText("Rs." + total);

        Config config = new Config("test_public_key_40d39152c69f48a7ba21e47a4a830c47", System.currentTimeMillis() + "", listItems.get(0).getName(), "", 10000L, new OnCheckOutListener() {
            @Override
            public void onSuccess(HashMap<String, Object> data) {
                database.child(auth.getUid()).child("cart").removeValue();
                HashMap<String, String> history = new HashMap<>();
                history.put("list", new Gson().toJson(listItems));
                history.put("totalPrice", Integer.toString(total));
                history.put("date", DateUtils.getDateToday());
                history.put("title", listItems.get(0).getName());
                history.put("image", listItems.get(0).getImage());
                history.put("qty", Integer.toString(listItems.size()));

                database.child(auth.getUid()).child("history").push().setValue(history);
                ToastUtils.showSuccessToast(PayNowActivity.this, "Payment Success!!!");
                finishAffinity();
                startActivity(new Intent(PayNowActivity.this, MainActivity.class));
            }

            @Override
            public void onError(String action, String message) {
                Log.i(action, message);
            }
        });
        khaltiButton.setCheckOutConfig(config);
    }

    private int getTotalPrice() {
        int total = 0;
        for (ItemEntity item : listItems) {
            total = total + item.getPrice();
        }
        return total;
    }

    public void goBack(View view) {
        super.onBackPressed();
    }
}
