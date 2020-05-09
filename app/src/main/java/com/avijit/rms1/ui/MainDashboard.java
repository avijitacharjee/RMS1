package com.avijit.rms1.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.TextView;

import com.avijit.rms1.R;
import com.avijit.rms1.data.remote.responses.CoronaSummaryResponse;
import com.avijit.rms1.viewmodel.MainDashBoardViewModel;

public class MainDashboard extends AppCompatActivity {
    TextView textView;
    MainDashBoardViewModel studentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);
        textView = findViewById(R.id.corona_summary);

        studentViewModel = ViewModelProviders.of(this).get(MainDashBoardViewModel.class);
        studentViewModel.init();
        studentViewModel.getStudentRepository().observe(this, new Observer<CoronaSummaryResponse>() {
            @Override
            public void onChanged(CoronaSummaryResponse studentResponse) {
                textView.setText(studentResponse.getMessage());
            }
        });

    }

}
