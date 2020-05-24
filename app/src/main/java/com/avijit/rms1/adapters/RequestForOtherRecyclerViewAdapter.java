package com.avijit.rms1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avijit.rms1.R;

import java.util.ArrayList;
import java.util.List;

public class RequestForOtherRecyclerViewAdapter extends RecyclerView.Adapter<RequestForOtherRecyclerViewAdapter.ViewHolder>{
    List<String> dataList;
    int size;
    public RequestForOtherRecyclerViewAdapter(int size){
        dataList = new ArrayList<>();
        this.size = size;
    }
    public List<String> getData(){
        return dataList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_request_for_other,parent,false);
        ViewHolder vh= new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        dataList.add(holder.editText.getText().toString());
    }

    @Override
    public int getItemCount() {
        return size;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public EditText editText;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            editText = itemView.findViewById(R.id.abcd);
        }
    }


}
