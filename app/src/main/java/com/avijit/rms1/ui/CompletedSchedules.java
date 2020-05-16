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
import com.avijit.rms1.viewmodel.CompletedSchedulesViewModel;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CompletedSchedules extends BaseActivity {
    CompletedSchedulesViewModel completedSchedulesViewModel;
    AlertDialog dialog;
    RecyclerView recyclerView;
    ReliefScheduleRecyclerViewAdapter adapter;

    List<String> sls = new ArrayList<>();
    List<String> dates = new ArrayList<>();
    List<String> names = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_schedules);
        appUtils = new AppUtils(this);
        this.dialog = appUtils.dialog;

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("RMS");
        toolbar.setSubtitle("Completed schedules for giving relief");
        //toolbar.setLogo(R.drawable.ic_exit_to_app_black_24dp);
        toolbar.setBackgroundColor(Color.BLACK);
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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
            }
        });
        Menu menu = navigationView.getMenu();
        MenuItem tools= menu.findItem(R.id.group_title_1);
        SpannableString s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s.length(), 0);
        tools.setTitle(s);
        completedSchedulesViewModel = ViewModelProviders.of(this).get(CompletedSchedulesViewModel.class);
        completedSchedulesViewModel.init();
        recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        String userId;
        try{
            JSONObject object = new JSONObject(getSharedPreferences("RMS",MODE_PRIVATE).getString("user",""));
            userId = object.getString("id");
        }

        catch (Exception e){userId="1";}
        fetchData(userId);

        adapter = new ReliefScheduleRecyclerViewAdapter(sls,dates,names);
        recyclerView.setAdapter(adapter);
    }
    private void fetchData(String id) {

        dialog.show();
        completedSchedulesViewModel.getCompletedSchedules(id).observe(CompletedSchedules.this, new Observer<ReliefScheduleResponse>() {
            @Override
            public void onChanged(ReliefScheduleResponse reliefScheduleResponse) {
                dialog.dismiss();
                for(int i=0;i<reliefScheduleResponse.getData().size();i++){
                    sls.add(""+(i+1));
                    dates.add(reliefScheduleResponse.getData().get(i).getSchedule_date());
                    names.add(reliefScheduleResponse.getData().get(i).getName());
                }
            }
        });
    }
}
