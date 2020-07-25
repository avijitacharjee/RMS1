package com.avijit.rms1.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.avijit.rms1.R;
import com.avijit.rms1.viewmodel.PackageUiViewModel;

public class PackageUi extends AppCompatActivity {
    private PackageUiViewModel viewModel;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_ui);
        viewModel = ViewModelProviders.of(this).get(PackageUiViewModel.class);
        initViews();
    }
    private void initViews(){
        recyclerView = findViewById(R.id.recycler_view);

    }
}