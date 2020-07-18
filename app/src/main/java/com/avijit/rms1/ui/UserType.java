package com.avijit.rms1.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.avijit.rms1.R;
import com.avijit.rms1.adapters.UserTypeRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserType extends AppCompatActivity {
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
        recyclerViewAdapter= new UserTypeRecyclerViewAdapter(userTypeList);
    }
    private void initViews(){
        recyclerView= findViewById(R.id.recycler_view);
    }
}
