package com.example.finalyearapplication.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalyearapplication.R;
import com.example.finalyearapplication.utils.ToastUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    EditText etName, etEmail, etPassword;
    Button btnRegister;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPass);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> register());
    }

    private void register() {
        String email = etEmail.getText().toString();
        String name = etName.getText().toString();
        String pass = etPassword.getText().toString();
        if (email.isEmpty()) {
            etEmail.setError("Please enter e-mail");
            etEmail.requestFocus();
            return;
        } else if (pass.isEmpty()) {
            etPassword.setError("Please enter password");
            etPassword.requestFocus();
            return;
        } else if (email.isEmpty() && pass.isEmpty()) {
            Toast.makeText((RegisterActivity.this), "Empty fields!", Toast.LENGTH_SHORT).show();
            return;
        } else{
            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setMessage("Creating account...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            mFirebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                dialog.dismiss();
                if (task.isSuccessful()) {
                    String uid = task.getResult().getUser().getUid();
                    HashMap<String, String> userData = new HashMap<>();
                    userData.put("email", email);
                    userData.put("name", name);
                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userData);
                    ToastUtils.showSuccessToast(this, "User registered successfully!!!");
                    finish();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                } else {
                    ToastUtils.showErrorToast(this, "Failed to register user.");
                }
            });
        }
    }

    public void openLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
