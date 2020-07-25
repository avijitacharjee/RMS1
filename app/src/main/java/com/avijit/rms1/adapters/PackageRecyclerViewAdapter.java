package com.avijit.rms1.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.avijit.rms1.R;
import com.avijit.rms1.data.remote.model.Package;
import com.avijit.rms1.ui.PackageUi;
import com.avijit.rms1.ui.UserCRUD;
import com.avijit.rms1.utils.AppUtils;
import com.avijit.rms1.viewmodel.PackageUiViewModel;

import java.util.List;

/**
 * Created by Avijit Acharjee on 7/25/2020 at 5:24 PM.
 * Email: avijitach@gmail.com.
 */
public class PackageRecyclerViewAdapter extends RecyclerView.Adapter<PackageRecyclerViewAdapter.ViewHolder>{
    private List<Package> packageList;
    private Activity activity;
    private PackageUiViewModel viewModel;
    private AppUtils appUtils ;
    public PackageRecyclerViewAdapter(Activity activity,List<Package> packageList) {
        this.activity = activity;
        this.packageList = packageList;
        viewModel = ViewModelProviders.of((PackageUi)activity).get(PackageUiViewModel.class);
        appUtils = new AppUtils(activity);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_package_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameTextView.setText(packageList.get(position).getName());
        holder.daysTextView.setText(packageList.get(position).getDays());
        holder.deleteButton.setOnClickListener(v->{
            new AlertDialog.Builder(activity)
                    .setPositiveButton("Yes",(dialog, which) -> {
                        appUtils.dialog.show();
                        viewModel.delete(packageList.get(position).getId()).observe((UserCRUD) activity, response->{
                            appUtils.dialog.dismiss();
                            if(response.isNetworkIsSuccessful()){
                                packageList.remove(position);
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
        holder.updateButton.setOnClickListener(v->{

        });
    }

    @Override
    public int getItemCount() {
        return packageList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView,daysTextView;
        ImageView deleteButton,updateButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_text_view);
            daysTextView = itemView.findViewById(R.id.days_text_view);
            deleteButton = itemView.findViewById(R.id.delete_button);
            updateButton = itemView.findViewById(R.id.update_button);
        }
    }
}
