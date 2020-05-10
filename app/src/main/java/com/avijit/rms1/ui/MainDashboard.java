package com.avijit.rms1.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.avijit.rms1.R;
import com.avijit.rms1.data.remote.responses.CoronaSummaryResponse;
import com.avijit.rms1.databinding.ActivityMainDashboardBinding;
import com.avijit.rms1.utils.AppUtils;
import com.avijit.rms1.viewmodel.MainDashBoardViewModel;

public class MainDashboard extends AppCompatActivity {
    TextView textView;
    MainDashBoardViewModel mainDashBoardViewModel;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);
        //textView = findViewById(R.id.msg);
        dialog = new AppUtils(this).dialog;
        dialog.show();
        mainDashBoardViewModel = ViewModelProviders.of(this).get(MainDashBoardViewModel.class);
        mainDashBoardViewModel.init();
        mainDashBoardViewModel.getStudentRepository().observe(this, new Observer<CoronaSummaryResponse>() {
            @Override
            public void onChanged(CoronaSummaryResponse coronaSummaryResponse) {
               dialog.dismiss();
            }
        });
        ActivityMainDashboardBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main_dashboard);
        binding.setLifecycleOwner(this);
        binding.setViewmodel(mainDashBoardViewModel);

    }



}
