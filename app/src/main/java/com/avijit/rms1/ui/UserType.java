package com.avijit.rms1.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
        appUtils= new AppUtils(this);
        appUtils.dialog.show();
        viewModel.getAll().observe(this,listNetworkResponse -> {
            appUtils.dialog.dismiss();
            if(!listNetworkResponse.isNetworkIsSuccessful()){
                Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();
                return;
            }
            if(listNetworkResponse.getData()==null) return;
            userTypeList.addAll(listNetworkResponse.getData());
        });
        recyclerViewAdapter= new UserTypeRecyclerViewAdapter(userTypeList,this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
    private void initViews(){
        recyclerView= findViewById(R.id.recycler_view);
    }
}
