package com.example.finalyearapplication.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearapplication.R;
import com.example.finalyearapplication.adapters.ItemAdapter;
import com.example.finalyearapplication.models.ItemEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class HistoryDetailsActivity extends AppCompatActivity {

    private List<ItemEntity> itemList;
    private ItemAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);

        Type type = new TypeToken<List<ItemEntity>>() {
        }.getType();
        itemList = new Gson().fromJson(getIntent().getStringExtra("list"), type);
        adapter = new ItemAdapter(this, itemList);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(adapter);
    }

    public void goBack(View view) {
        super.onBackPressed();
    }
}
