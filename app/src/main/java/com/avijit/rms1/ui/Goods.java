package com.avijit.rms1.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.avijit.rms1.R;
import com.avijit.rms1.adapters.GoodsRecyclerViewAdapter;
import com.avijit.rms1.data.remote.model.Good;
import com.avijit.rms1.data.remote.model.User;
import com.avijit.rms1.utils.AppUtils;
import com.avijit.rms1.viewmodel.GoodsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Goods extends BaseActivity {
    GoodsViewModel viewModel;
    RecyclerView recyclerView;
    GoodsRecyclerViewAdapter adapter;
    List<Good> goodList = new ArrayList<>();
    FloatingActionButton fab;
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
        fab.setOnClickListener(v -> {
            DialogFragment dialogFragment=  new AddGoodsDialogFragment(this);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            dialogFragment.show(ft,"update");
        });
    }
    private void initViews(){
        recyclerView=findViewById(R.id.recycler_view);
        fab = findViewById(R.id.fab);
    }
    public static class AddGoodsDialogFragment extends DialogFragment {
        Activity activity;
        EditText nameEditText,unitEditText;
        TextView goButton;
        GoodsViewModel viewModel;
        AppUtils appUtils;
        public AddGoodsDialogFragment(Activity activity){
            this.activity=activity;
        }
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_dialog_add_good,container,false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            initViews(view);
            User user;
            try {
                user = new Gson().fromJson(activity.getSharedPreferences("RMS",MODE_PRIVATE).getString("user",""),User.class);
            }catch (Exception e){
                user = new User();
                user.setId("1");
            }
            User finalUser = user;
            goButton.setOnClickListener(v -> {
                Good good = new Good();
                good.setName(nameEditText.getText().toString());
                good.setUnit(unitEditText.getText().toString());
                good.setCreated_by(finalUser.getId());
                appUtils.dialog.show();
                viewModel.create(good).observe(this,response->{
                    appUtils.dialog.dismiss();
                    if(response.isNetworkIsSuccessful()){
                        Toast.makeText(activity, "Added Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(activity, "Failed to connect", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }
        private void initViews(View view){
            nameEditText = view.findViewById(R.id.name_edit_text);
            unitEditText = view.findViewById(R.id.unit_edit_text);
            goButton = view.findViewById(R.id.go_button);
            viewModel = ViewModelProviders.of((Goods)activity).get(GoodsViewModel.class);
            appUtils = new AppUtils(activity);
        }
    }
}