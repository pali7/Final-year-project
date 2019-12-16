package com.example.finalyearapplication.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalyearapplication.R;
import com.example.finalyearapplication.utils.ToastUtils;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> login());

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void login() {
        if (TextUtils.isEmpty(etEmail.getText().toString().trim())) {
            etEmail.setError("Email is required!!!");
            etEmail.requestFocus();
            return;
        } else if (TextUtils.isEmpty(etPassword.getText().toString().trim())) {
            etPassword.setError("Password is required!!!");
            etPassword.requestFocus();
            return;
        }
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Logging in...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            dialog.dismiss();
            if (task.isSuccessful()) {
                finish();
                startActivity(new Intent(this, MainActivity.class));
                ToastUtils.showSuccessToast(this, "Login Success!!!");
            } else {
                ToastUtils.showErrorToast(this, "Invalid email and password.");
            }
        });
    }

    public void openRegister(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
