package com.avijit.rms1.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.avijit.rms1.R;
import com.avijit.rms1.adapters.ReliefScheduleRecyclerViewAdapter;
import com.avijit.rms1.data.remote.responses.ReliefScheduleResponse;
import com.avijit.rms1.utils.AppUtils;
import com.avijit.rms1.utils.EndDrawerToggle;
import com.avijit.rms1.viewmodel.PendingSchedulesViewModel;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PendingSchedules extends BaseActivity {
    PendingSchedulesViewModel pendingSchedulesViewModel;
    AppUtils appUtils;
    AlertDialog dialog;
    DrawerLayout drawer;
    RecyclerView recyclerView;
    ReliefScheduleRecyclerViewAdapter adapter;

    List<String> sls = new ArrayList<>();
    List<String> dates = new ArrayList<>();
    List<String> names = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_schedules);
        appUtils = new AppUtils(this);
        this.dialog = appUtils.dialog;

        super.setToolbar();
        pendingSchedulesViewModel = ViewModelProviders.of(this).get(PendingSchedulesViewModel.class);
        pendingSchedulesViewModel.init();

        toolbar.setTitle("RMS");
        toolbar.setSubtitle("Pending schedules for giving relief");
        //toolbar.setLogo(R.drawable.ic_exit_to_app_black_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(appUtils.navigationItemSelectedListener);

        EndDrawerToggle toggle = new EndDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationOnClickListener(v -> PendingSchedules.super.onBackPressed());
        Menu menu = navigationView.getMenu();
        MenuItem tools= menu.findItem(R.id.group_title_1);
        SpannableString s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s.length(), 0);
        tools.setTitle(s);
        recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String userId;
        try{
            JSONObject object = new JSONObject(getSharedPreferences("RMS",MODE_PRIVATE).getString("user",""));
            userId = object.getString("id");
        }

        catch (Exception e){
            userId="1";
        }
        fetchData(userId);

        adapter = new ReliefScheduleRecyclerViewAdapter(sls,dates,names);
        recyclerView.setAdapter(adapter);
        setNavDrawer(navigationView);

    }
    private void fetchData(String id) {
        dialog.show();
        pendingSchedulesViewModel.getPendingSchedules(id).observe(PendingSchedules.this, new Observer<ReliefScheduleResponse>() {
            @Override
            public void onChanged(ReliefScheduleResponse reliefScheduleResponse) {
                dialog.dismiss();
                if(!reliefScheduleResponse.isNetworkIsSuccessful()){
                    Toast.makeText(PendingSchedules.this, "Failed to connect", Toast.LENGTH_SHORT).show();
                    return;
                }
                for(int i=0;i<reliefScheduleResponse.getData().size();i++){
                    sls.add(""+(i+1));
                    dates.add(reliefScheduleResponse.getData().get(i).getSchedule_date());
                    names.add(reliefScheduleResponse.getData().get(i).getName());
                }
                adapter.notifyDataSetChanged();
            }
        });

    }

}