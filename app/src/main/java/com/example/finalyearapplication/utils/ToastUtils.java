package com.example.finalyearapplication.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalyearapplication.R;

public class ToastUtils {
    public static void showErrorToast(Context context, String message) {
        View layout = LayoutInflater.from(context).inflate(R.layout.toast_error, null);

        TextView text = layout.findViewById(R.id.tvMessage);
        text.setText(message);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 200);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public static void showSuccessToast(Context context, String message) {
        View layout = LayoutInflater.from(context).inflate(R.layout.toast_success, null);

        TextView text = layout.findViewById(R.id.tvMessage);
        text.setText(message);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 200);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
