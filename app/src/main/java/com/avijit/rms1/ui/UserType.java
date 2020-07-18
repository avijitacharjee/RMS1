package com.avijit.rms1.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.avijit.rms1.R;
import com.avijit.rms1.adapters.UserTypeRecyclerViewAdapter;
import com.avijit.rms1.utils.AppUtils;
import com.avijit.rms1.viewmodel.UserTypeViewModel;

import java.util.ArrayList;
import java.util.List;

public class UserType extends BaseActivity {
    UserTypeViewModel viewModel;
    RecyclerView recyclerView;
    UserTypeRecyclerViewAdapter recyclerViewAdapter;
    List<com.avijit.rms1.data.remote.model.UserType> userTypeList= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);
        initViews();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        setToolbar();
        toolbar.setTitle("RMS");
        toolbar.setSubtitle("User Type");
        appUtils= new AppUtils(this);
        appUtils.dialog.show();
        viewModel= ViewModelProviders.of(this).get(UserTypeViewModel.class);
        viewModel.getAll().observe(this,listNetworkResponse -> {
            appUtils.dialog.dismiss();
            if(!listNetworkResponse.isNetworkIsSuccessful()){
                Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();
                return;
            }
            if(listNetworkResponse.getData()==null) return;
            userTypeList.addAll(listNetworkResponse.getData());
            recyclerViewAdapter.notifyDataSetChanged();
        });
        recyclerViewAdapter= new UserTypeRecyclerViewAdapter(userTypeList,this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
    private void initViews(){
        recyclerView= findViewById(R.id.user_type_recycler_view);
    }
}
