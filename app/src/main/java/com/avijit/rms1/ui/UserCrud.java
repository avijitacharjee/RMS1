package com.avijit.rms1.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.avijit.rms1.R;
import com.avijit.rms1.adapters.UserCrudRecyclerViewAdapter;
import com.avijit.rms1.data.remote.model.User;
import com.avijit.rms1.utils.AppUtils;
import com.avijit.rms1.viewmodel.UserCrudViewModel;

import java.util.ArrayList;
import java.util.List;

public class UserCrud extends BaseActivity {
    UserCrudViewModel viewModel;
    RecyclerView recyclerView;
    UserCrudRecyclerViewAdapter adapter;
    List<User> userList= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_crud);
        initViews();
        viewModel = ViewModelProviders.of(this).get(UserCrudViewModel.class);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        /*setToolbar();
        toolbar.setTitle("RMS");
        toolbar.setSubtitle("User Type");*/

        appUtils= new AppUtils(this);
        appUtils.dialog.show();
        adapter= new UserCrudRecyclerViewAdapter(this,userList);
        recyclerView.setAdapter(adapter);
        viewModel.getAll().observe(this,response->{
            appUtils.dialog.dismiss();
            if(response.isNetworkIsSuccessful()){
                userList.addAll(response.getData());
                adapter.notifyDataSetChanged();
            }
            else {
                Toast.makeText(this, "Failed to load users", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initViews(){
        recyclerView = findViewById(R.id.recycler_view);
    }
}
