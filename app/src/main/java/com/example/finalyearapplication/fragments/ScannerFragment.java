package com.example.finalyearapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.example.finalyearapplication.R;
import com.example.finalyearapplication.models.ItemEntity;
import com.example.finalyearapplication.utils.ToastUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class ScannerFragment extends Fragment {

    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;
    private DatabaseReference database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_scanner, container, false);
        scannerView = v.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(getActivity(), scannerView);

        database = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("cart");
// opens camera
        mCodeScanner.setDecodeCallback(result -> getActivity().runOnUiThread(() -> {
            try {
                ItemEntity entity = new Gson().fromJson(result.getText(), ItemEntity.class);
                database.push().setValue(entity);
                ToastUtils.showSuccessToast(getActivity(), entity.getName() + " added in your cart.");
            } catch (Exception ex) {
                ToastUtils.showErrorToast(getActivity(), "Invalid item scanned!!!");
            }
        }));
        scannerView.setOnClickListener(view -> mCodeScanner.startPreview());
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}
