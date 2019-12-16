package com.example.finalyearapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.finalyearapplication.R;
import com.example.finalyearapplication.fragments.AboutUsFragment;
import com.example.finalyearapplication.fragments.HistoryFragment;
import com.example.finalyearapplication.fragments.MyCartFragment;
import com.example.finalyearapplication.fragments.ScannerFragment;
import com.example.finalyearapplication.models.ItemEntity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout drawerLayout;
    private ImageView imgMenu;
    private TextView tvTitle;
    private TextView tvPayNow;
    private NavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawerLayout);
        imgMenu = findViewById(R.id.imgMenu);
        tvTitle = findViewById(R.id.tvTitle);
        tvPayNow = findViewById(R.id.tvPayNow);
        nav = findViewById(R.id.nav);

        imgMenu.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        openFragment(new ScannerFragment(), "Scanner");

        nav.findViewById(R.id.mnuScanner).setOnClickListener(this);
        nav.findViewById(R.id.mnuMyCart).setOnClickListener(this);
        nav.findViewById(R.id.mnuHistory).setOnClickListener(this);
        nav.findViewById(R.id.mnuAboutUs).setOnClickListener(this);
        nav.findViewById(R.id.mnuLogout).setOnClickListener(this);
        tvPayNow.setOnClickListener(this);
    }


    private void openFragment(Fragment fragment, String title) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
        tvTitle.setText(title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mnuScanner:
                openFragment(new ScannerFragment(), "Scanner");
                hidePayNow();
                break;
            case R.id.mnuMyCart:
                hidePayNow();
                openFragment(new MyCartFragment(), "My Cart");
                break;
            case R.id.mnuHistory:
                openFragment(new HistoryFragment(), "History");
                hidePayNow();
                break;
            case R.id.mnuAboutUs:
                openFragment(new AboutUsFragment(), "About Us");
                hidePayNow();
                break;
            case R.id.mnuLogout:
                View view = LayoutInflater.from(this).inflate(R.layout.dialog_logout, null, false);
                AlertDialog dialog = new AlertDialog.Builder(this).create();
                dialog.setView(view);
                view.findViewById(R.id.tvNo).setOnClickListener(vv -> dialog.dismiss());
                view.findViewById(R.id.tvYes).setOnClickListener(vv -> {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                });
                dialog.show();
                break;
            case R.id.tvPayNow:
                MyCartFragment cartFragment = (MyCartFragment) getSupportFragmentManager().findFragmentById(R.id.container);
                List<ItemEntity> entityList = cartFragment.getItems();

                Intent intent = new Intent(this, PayNowActivity.class);
                intent.putExtra("list", (Serializable) entityList);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void showPayNow() {
        tvPayNow.setVisibility(View.VISIBLE);
    }

    public void hidePayNow() {
        tvPayNow.setVisibility(View.GONE);
    }
}