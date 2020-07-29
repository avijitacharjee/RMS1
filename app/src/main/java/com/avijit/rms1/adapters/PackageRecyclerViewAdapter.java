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
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.avijit.rms1.R;
import com.avijit.rms1.data.remote.model.Package;
import com.avijit.rms1.ui.PackageUi;
import com.avijit.rms1.utils.AppUtils;
import com.avijit.rms1.viewmodel.PackageUiViewModel;

import java.util.List;
import java.util.Objects;

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
                        viewModel.delete(packageList.get(position).getId()).observe((PackageUi) activity, response->{
                            appUtils.dialog.dismiss();
                            if(response==null){
                                activity.startActivity(new Intent(activity.getApplicationContext(),PackageUi.class));
                                return;
                            }
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
            DialogFragment dialogFragment=  new PackageRecyclerViewAdapter.UpdateDialogFragment(packageList.get(position));
            FragmentTransaction ft = ((PackageUi) activity ).getSupportFragmentManager().beginTransaction();
            Fragment prev =((PackageUi) activity ). getSupportFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            dialogFragment.show(ft,"update");
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
    public static class UpdateDialogFragment extends DialogFragment {
        EditText nameEditText,daysEditText;
        TextView goButton;
        Package aPackage;
        PackageUiViewModel viewModel;
        AppUtils appUtils;
        public UpdateDialogFragment(Package aPackage){
            this.aPackage=aPackage;

        }
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_dialog_package_update,container,true);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            initViews(view);
            viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(PackageUiViewModel.class);
            appUtils = new AppUtils(this.getContext());
            goButton.setOnClickListener(v->{
                Package pkg= new Package();
                pkg.setName(nameEditText.getText().toString());
                pkg.setDays(daysEditText.getText().toString());
                appUtils.dialog.show();
                viewModel.update(aPackage.getId(),pkg).observe(this,response->{
                    appUtils.dialog.dismiss();
                    if(response.isNetworkIsSuccessful()){
                        Toast.makeText(this.getContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this.getContext(), "Failed to connect", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }
        private void initViews(View view){
            nameEditText = view.findViewById(R.id.name_edit_text);
            daysEditText = view.findViewById(R.id.days_edit_text);
            goButton = view.findViewById(R.id.go_button);
            //previous texts
            nameEditText.setText(aPackage.getName());
            daysEditText.setText(aPackage.getDays());
        }
    }
}
