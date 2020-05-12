package com.avijit.rms1.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.MutableLiveData;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.avijit.rms1.R;
import com.avijit.rms1.data.remote.RetrofitService;
import com.avijit.rms1.data.remote.api.CompanyApi;
import com.avijit.rms1.data.remote.api.CoronaSummaryApi;
import com.avijit.rms1.data.remote.responses.CompanyResponse;
import com.avijit.rms1.data.remote.responses.CompanySearchResponse;
import com.avijit.rms1.data.remote.responses.CompanyStoreResponse;
import com.avijit.rms1.data.remote.responses.CoronaSummaryResponse;
import com.avijit.rms1.repository.CompanyRepository;
import com.avijit.rms1.repository.CoronaSummaryRepository;
import com.avijit.rms1.utils.AppUtils;
import com.avijit.rms1.utils.EndDrawerToggle;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Nav extends AppCompatActivity {
    DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        AppUtils appUtils = new AppUtils(this);


        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("RMS");
        toolbar.setSubtitle("Home");
        //toolbar.setLogo(R.drawable.ic_exit_to_app_black_24dp);
        toolbar.setBackgroundColor(Color.BLACK);
        setSupportActionBar(toolbar);
        /*getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(appUtils.navigationItemSelectedListener);
       /* mAppBarConfiguration = new AppBarConfiguration.Builder()
                .setDrawerLayout(drawer)
                .build();*/
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
        if(getSharedPreferences("RMS",MODE_PRIVATE).getString("token","").equals("")){
            startActivity(new Intent(Nav.this,Login.class));
        }



    }
}
