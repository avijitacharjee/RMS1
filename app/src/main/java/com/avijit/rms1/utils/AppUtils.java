package com.avijit.rms1.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.avijit.rms1.MainActivity;
import com.avijit.rms1.R;
import com.avijit.rms1.ui.MainDashboard;
import com.google.android.material.navigation.NavigationView;

import static android.content.Context.MODE_PRIVATE;

public class AppUtils {
    Context context;
    public AlertDialog dialog;

    public AppUtils(Context context) {
        this.context = context;
        setProgressDialog();
    }
    public void setProgressDialog() {

        int llPadding = 30;
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setPadding(llPadding, llPadding, llPadding, llPadding);
        ll.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams llParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        llParam.gravity = Gravity.CENTER;
        ll.setLayoutParams(llParam);

        ProgressBar progressBar = new ProgressBar(context);
        progressBar.setIndeterminate(true);
        progressBar.setPadding(0, 0, llPadding, 0);
        progressBar.setLayoutParams(llParam);

        llParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        llParam.gravity = Gravity.CENTER;
        TextView tvText = new TextView(context);
        tvText.setText("Loading ...");
        tvText.setTextColor(Color.parseColor("#000000"));
        tvText.setTextSize(20);
        tvText.setLayoutParams(llParam);

        ll.addView(progressBar);
        ll.addView(tvText);

        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(ll);

        dialog = builder.create();
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(layoutParams);
        }
    }
    public final NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            switch (id)
            {
                /*case R.id.logout: {
                    context.getSharedPreferences("RMS",MODE_PRIVATE).edit().putString("token","").apply();
                    context.startActivity(new Intent(context, MainActivity.class));
                    break;
                }
                case R.id.home: {
                    context.startActivity(new Intent(context,MainActivity.class));
                    break;
                }
                case R.id.nav_add_donate_schedule: {
                    context.startActivity(new Intent(context, AddDonateSchedule.class));
                    break;
                }
                case R.id.nav_show_pending_donate_schedule: {
                    context.startActivity(new Intent(context, ShowPendingDonateSchedule.class));
                    break;
                }
                case R.id.nav_show_completed_donate_schedule: {
                    context.startActivity(new Intent(context, ShowCompletedDonateSchedule.class));
                    break;
                }*/
            }
            return true;
        }
    };
}
