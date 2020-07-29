package com.avijit.rms1.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.avijit.rms1.R;
import com.avijit.rms1.data.remote.model.Good;
import com.avijit.rms1.ui.Goods;
import com.avijit.rms1.ui.UserCRUD;
import com.avijit.rms1.utils.AppUtils;
import com.avijit.rms1.viewmodel.GoodsViewModel;

import java.util.List;
import java.util.Objects;

/**
 * Created by Avijit Acharjee on 7/23/2020 at 12:01 AM.
 * Email: avijitach@gmail.com.
 */
public class GoodsRecyclerViewAdapter extends RecyclerView.Adapter<GoodsRecyclerViewAdapter.ViewHolder>{
    List<Good> goodList;
    Activity activity;
    GoodsViewModel viewModel;
    AppUtils appUtils;
    public GoodsRecyclerViewAdapter(Activity activity,List<Good> goodList){
        this.goodList=goodList;
        this.activity=activity;
        viewModel = ViewModelProviders.of((Goods)activity).get(GoodsViewModel.class);
        appUtils = new AppUtils(activity);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameTextView.setText(goodList.get(position).getName());
        holder.unitTextView.setText(goodList.get(position).getUnit());
        holder.deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(activity)
                    .setPositiveButton("Yes",(dialog, which) -> {
                        appUtils.dialog.show();
                        viewModel.delete(goodList.get(position).getId()).observe((Goods) activity, response->{
                            appUtils.dialog.dismiss();
                            if(response.isNetworkIsSuccessful()){
                                goodList.remove(position);
                                Toast.makeText(activity, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            }
                            else {
                                Toast.makeText(activity, "Failed to connect", Toast.LENGTH_SHORT).show();
                            }
                        });
                    })
                    .setNegativeButton("No",(dialog, which) -> {

                    })
                    .setMessage("Are you sure to delete?")
                    .create()
                    .show();
        });
        holder.updateButton.setOnClickListener(v -> {
            DialogFragment dialogFragment=  new GoodsRecyclerViewAdapter.UpdateDialogFragment(goodList.get(position));
            FragmentTransaction ft = ((Goods) activity ).getSupportFragmentManager().beginTransaction();
            Fragment prev =((Goods) activity ). getSupportFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            dialogFragment.show(ft,"update");
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return goodList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView,unitTextView;
        ImageView deleteButton,updateButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_text_view);
            unitTextView = itemView.findViewById(R.id.unit_text_view);
            deleteButton = itemView.findViewById(R.id.delete_button);
            updateButton = itemView.findViewById(R.id.update_button);
        }
    }
    public static class UpdateDialogFragment extends DialogFragment {
        EditText nameEditText,unitEditText;
        TextView goButton;
        Good good;
        AppUtils appUtils ;
        GoodsViewModel viewModel;
        public UpdateDialogFragment(Good good){
            this.good=good;

        }
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_dialog_good_update,container,false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            initViews(view);
            appUtils = new AppUtils(getContext());
            viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(GoodsViewModel.class);
            goButton.setOnClickListener(v -> {
                Good good1 = new Good();
                good1.setId(good.getId());
                good1.setName(nameEditText.getText().toString());
                good1.setUnit(unitEditText.getText().toString());
                appUtils.dialog.show();
                viewModel.update(good.getId(),good1).observe(this,response->{
                    appUtils.dialog.dismiss();
                    if(response==null){
                        startActivity(new Intent(getContext(),Goods.class));
                        return;
                    }
                    if(response.isNetworkIsSuccessful()){
                        Toast.makeText(getContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getContext(), "Failed to connect", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }
        private void initViews(View view){
            nameEditText = view.findViewById(R.id.name_edit_text);
            unitEditText = view.findViewById(R.id.unit_edit_text);
            goButton = view.findViewById(R.id.go_button);
        }
    }
}
