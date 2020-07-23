package com.avijit.rms1.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.avijit.rms1.data.remote.model.Good;
import com.avijit.rms1.ui.Goods;
import com.avijit.rms1.viewmodel.GoodsViewModel;

import java.util.List;

/**
 * Created by Avijit Acharjee on 7/23/2020 at 12:01 AM.
 * Email: avijitach@gmail.com.
 */
public class GoodsRecyclerViewAdapter extends RecyclerView.Adapter<GoodsRecyclerViewAdapter.ViewHolder>{
    List<Good> goodList;
    Activity activity;
    GoodsViewModel viewModel;
    public GoodsRecyclerViewAdapter(Activity activity,List<Good> goodList){
        this.goodList=goodList;
        this.activity=activity;
        viewModel = ViewModelProviders.of((Goods)activity).get(GoodsViewModel.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return goodList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
