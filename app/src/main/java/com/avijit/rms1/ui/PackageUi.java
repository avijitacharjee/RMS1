package com.avijit.rms1.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avijit.rms1.R;
import com.avijit.rms1.adapters.PackageRecyclerViewAdapter;
import com.avijit.rms1.data.remote.model.Package;
import com.avijit.rms1.data.remote.model.User;
import com.avijit.rms1.utils.AppUtils;
import com.avijit.rms1.viewmodel.PackageUiViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PackageUi extends BaseActivity {
    private PackageUiViewModel viewModel;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private PackageRecyclerViewAdapter adapter;
    private List<Package> packageList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_ui);
        viewModel = ViewModelProviders.of(this).get(PackageUiViewModel.class);
        initViews();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new PackageRecyclerViewAdapter(this,packageList);
        recyclerView.setAdapter(adapter);
        appUtils = new AppUtils(this);
        appUtils.dialog.show();
        viewModel.all().observe(this,response->{
            appUtils.dialog.dismiss();
            if(!response.isNetworkIsSuccessful()){
                Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();
                return;
            }
            if(response.getData()==null){
                return;
            }
            this.packageList.addAll(response.getData());
            adapter.notifyDataSetChanged();
        });
        fab.setOnClickListener(v->{
            DialogFragment dialogFragment=  new AddPackageDialogFragment();
            FragmentTransaction ft = (this ).getSupportFragmentManager().beginTransaction();
            Fragment prev =(this ). getSupportFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            dialogFragment.show(ft,"update");
        });
    }
    private void initViews(){
        recyclerView = findViewById(R.id.recycler_view);
        fab = findViewById(R.id.fab);
    }
    public static class AddPackageDialogFragment extends DialogFragment {
        EditText nameEditText,daysEditText;
        TextView goButton;
        PackageUiViewModel viewModel;
        AppUtils appUtils;
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
            User user ;
            try {
                user = new Gson().fromJson(getContext().getSharedPreferences("RMS",MODE_PRIVATE).getString("user",""),User.class);
            }catch (Exception e){
                user = new User();
            }
            User finalUser = user;
            goButton.setOnClickListener(v->{
                Package pkg= new Package();
                pkg.setName(nameEditText.getText().toString());
                pkg.setDays(daysEditText.getText().toString());
                pkg.setCreatedBy(finalUser.getId());
                appUtils.dialog.show();
                viewModel.create(pkg).observe(this,response->{
                    appUtils.dialog.dismiss();
                    if(response.isNetworkIsSuccessful()){
                        Toast.makeText(this.getContext(), "Added successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getContext(),PackageUi.class));
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

        }
    }
}