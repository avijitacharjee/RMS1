package com.avijit.rms1.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.avijit.rms1.MainActivity;
import com.avijit.rms1.R;
import com.avijit.rms1.ui.AddCompany;
import com.avijit.rms1.ui.AddSchedule;
import com.avijit.rms1.ui.AddUserInCompany;
import com.avijit.rms1.ui.CompletedSchedules;
import com.avijit.rms1.ui.FamilyAddress;
import com.avijit.rms1.ui.Goods;
import com.avijit.rms1.ui.Login;
import com.avijit.rms1.ui.MainDashboard;
import com.avijit.rms1.ui.Nav;
import com.avijit.rms1.ui.News;
import com.avijit.rms1.ui.PackageUi;
import com.avijit.rms1.ui.PendingSchedules;
import com.avijit.rms1.ui.Profile;
import com.avijit.rms1.ui.ReliefRequest;
import com.avijit.rms1.ui.SearchByNid;
import com.avijit.rms1.ui.UserCRUD;
import com.avijit.rms1.ui.UserType;
import com.google.android.material.navigation.NavigationView;

import static android.content.Context.MODE_PRIVATE;

public class AppUtils {
    Context context;
    public AlertDialog dialog;

    public AppUtils(Context context) {
        this.context = context;
        setProgressDialog();
    }

    public void makeToast(@NonNull String message){

        Toast toast = Toast.makeText(context,message , Toast.LENGTH_SHORT);
        View view = toast.getView();

        //Gets the actual oval background of the Toast then sets the colour filter
        view.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

        //Gets the TextView from the Toast so it can be editted
        TextView text = view.findViewById(android.R.id.message);
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        text.setTextColor(Color.WHITE);

        toast.show();

    }
    /**
     * return void
     * @param message default for null
     */
    public void showValidationMessage(@Nullable String message){
        if(message==null){
            message= "All Fields are required";
        }
        Toast toast = Toast.makeText(context,message , Toast.LENGTH_SHORT);
        View view = toast.getView();

        //Gets the actual oval background of the Toast then sets the colour filter
        view.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

        //Gets the TextView from the Toast so it can be editted
        TextView text = view.findViewById(android.R.id.message);
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        text.setTextColor(Color.WHITE);

        toast.show();

    }
    public static final RetryPolicy STRING_REQUEST_RETRY_POLICY = new RetryPolicy() {
        @Override
        public int getCurrentTimeout() {
            return 50000;
        }

        @Override
        public int getCurrentRetryCount() {
            return 5;
        }

        @Override
        public void retry(VolleyError error) throws VolleyError {

        }
    };
    public final Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(context, ""+error, Toast.LENGTH_SHORT).show();
            if(dialog.isShowing()){
                dialog.dismiss();
            }
        }
    };
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
                case R.id.home: {
                    context.startActivity(new Intent(context, Nav.class));
                    break;
                }
                case R.id.nav_add_donate_schedule: {
                    context.startActivity(new Intent(context, AddSchedule.class));
                    break;
                }
                case R.id.nav_show_pending_donate_schedule: {
                    context.startActivity(new Intent(context, PendingSchedules.class));
                    break;
                }
                case R.id.nav_show_completed_donate_schedule: {
                    context.startActivity(new Intent(context, CompletedSchedules.class));
                    break;
                }
                case R.id.logout: {
                    context.getSharedPreferences("RMS",MODE_PRIVATE).edit().putString("token","").apply();
                    context.startActivity(new Intent(context, Login.class));
                    break;
                }
                case R.id.nav_entry_giving_reliefs: {
                    context.startActivity(new Intent(context, FamilyAddress.class));
                    break;
                }
                case R.id.nav_add_company: {
                    context.startActivity(new Intent(context, AddCompany.class));
                    break;
                }
                case R.id.nav_add_request_for_relief :{
                    context.startActivity(new Intent(context, ReliefRequest.class));
                    break;
                }
                case R.id.nav_search_by_nid :{
                    context.startActivity(new Intent(context, SearchByNid.class));
                    break;
                }
                case R.id.nav_add_user_in_company : {
                    context.startActivity(new Intent(context, AddUserInCompany.class));
                    break;
                }
                case R.id.nav_news_home: {
                    context.startActivity(new Intent(context,News.class));
                    break;
                }
                case R.id.nav_profile: {
                    context.startActivity(new Intent(context, Profile.class));
                    break;
                }
                case R.id.nav_user_type: {
                    context.startActivity(new Intent(context, UserType.class));
                    break;
                }
                case R.id.nav_user :{
                    context.startActivity(new Intent(context, UserCRUD.class));
                    break;
                }
                case R.id.nav_goods : {
                    context.startActivity(new Intent(context, Goods.class));
                    break;
                }
                case R.id.nav_package: {
                    context.startActivity(new Intent(context, PackageUi.class));
                    break;
                }

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
