package com.avijit.rms1.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import com.avijit.rms1.R;
import com.avijit.rms1.viewmodel.PackageUiViewModel;

public class PackageUi extends AppCompatActivity {
    private PackageUiViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_ui);
        viewModel = ViewModelProviders.of(this).get(PackageUiViewModel.class);

    }
}