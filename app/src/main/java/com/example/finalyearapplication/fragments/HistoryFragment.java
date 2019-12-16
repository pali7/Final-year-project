package com.example.finalyearapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.finalyearapplication.R;
import com.example.finalyearapplication.adapters.HistoryAdapter;
import com.example.finalyearapplication.listeners.MyValueEventListener;
import com.example.finalyearapplication.models.HistoryEntity;
import com.example.finalyearapplication.utils.ToastUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HistoryFragment extends Fragment {

    private DatabaseReference database;
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;
    private List<HistoryEntity> listItems;
    private HistoryAdapter adapter;
    private View viewEmpty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_cart, container, false);
        database = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("history");

        swipeRefresh = v.findViewById(R.id.swipeRefresh);
        recyclerView = v.findViewById(R.id.recyclerView);
        viewEmpty = v.findViewById(R.id.viewEmpty);

        listItems = new ArrayList<>();
        adapter = new HistoryAdapter(getActivity(), listItems);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        swipeRefresh.setOnRefreshListener(this::getHistory);
        swipeRefresh.post(this::getHistory);

        return v;
    }

    public void getHistory() {
        swipeRefresh.setRefreshing(true);
        recyclerView.setVisibility(View.VISIBLE);
        viewEmpty.setVisibility(View.GONE);
        database.addListenerForSingleValueEvent(new MyValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                super.onDataChange(dataSnapshot);
                listItems.clear();
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    DataSnapshot snapshot = iterator.next();
                    HistoryEntity item = snapshot.getValue(HistoryEntity.class);
                    listItems.add(item);
                }
                adapter.notifyDataSetChanged();
                if (listItems.size() == 0) {
                    recyclerView.setVisibility(View.GONE);
                    viewEmpty.setVisibility(View.VISIBLE);
                    ToastUtils.showErrorToast(getActivity(), "Your history is empty!!!");
                }
                swipeRefresh.setRefreshing(false);
            }
        });
    }
}