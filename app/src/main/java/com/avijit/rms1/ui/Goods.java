package com.avijit.rms1.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.avijit.rms1.R;
import com.avijit.rms1.adapters.GoodsRecyclerViewAdapter;
import com.avijit.rms1.data.remote.model.Good;
import com.avijit.rms1.utils.AppUtils;
import com.avijit.rms1.viewmodel.GoodsViewModel;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Goods extends BaseActivity {
    GoodsViewModel viewModel;
    RecyclerView recyclerView;
    GoodsRecyclerViewAdapter adapter;
    List<Good> goodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        initViews();
        viewModel= ViewModelProviders.of(this).get(GoodsViewModel.class);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new GoodsRecyclerViewAdapter(this,goodList);
        recyclerView.setAdapter(adapter);
        appUtils = new AppUtils(this);
        appUtils.dialog.show();
        viewModel.getGoodList().observe(this,response->{
            appUtils.dialog.dismiss();
            if(response.isNetworkIsSuccessful()){
                goodList.addAll(response.getData());
                adapter.notifyDataSetChanged();
            }
            else {
                Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initViews(){
        recyclerView=findViewById(R.id.recycler_view);

    }
}