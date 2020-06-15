package com.avijit.rms1.ui;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.avijit.rms1.R;
import com.avijit.rms1.data.local.AppDatabase;
import com.avijit.rms1.utils.AppUtils;
import com.avijit.rms1.utils.EndDrawerToggle;
import com.avijit.rms1.utils.MyBroadcastReceiver;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avijit.rms1.data.local.entities.*;
public class Nav extends BaseActivity {
    MyBroadcastReceiver myBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        AppUtils appUtils = new AppUtils(this);
        setToolbar();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("RMS");
        toolbar.setSubtitle("Home");
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
        Menu menu = navigationView.getMenu();
        MenuItem tools= menu.findItem(R.id.group_title_1);
        SpannableString s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s.length(), 0);
        tools.setTitle(s);
        if(getSharedPreferences("RMS",MODE_PRIVATE).getString("token","").equals("")){
            startActivity(new Intent(Nav.this,Login.class));
        }
        //setLocations();
        //saveUserInfo();
        //broadcastIntent();
    }
    public void saveUserInfo(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://aniksen.me/covidbd/api/user";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getSharedPreferences("RMS",MODE_PRIVATE).edit().putString("user",response).apply();}
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Nav.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization","Bearer "+getSharedPreferences("RMS",MODE_PRIVATE).getString("token",""));
                return headers;
            }
        };
        stringRequest.setRetryPolicy(AppUtils.STRING_REQUEST_RETRY_POLICY);
        queue.add(stringRequest);
    }
    public void setLocations(){
        RequestQueue queue = Volley.newRequestQueue(Nav.this);
        String url = "https://aniksen.me/covidbd/api/locations";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    final AppDatabase db = AppDatabase.getInstance(Nav.this);

                    List<Division> divisionList = new ArrayList<>();
                    List<District> districtList = new ArrayList<>();
                    List<Area> areaList = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray locations = jsonObject.getJSONArray("locations");
                    for(int i=0;i<locations.length();i++)
                    {
                        JSONObject divisionResponse = locations.getJSONObject(i);
                        final Division division = new Division(divisionResponse.getString("division_id"),divisionResponse.getString("division_name"));
                        divisionList.add(division);
                        JSONArray districts = divisionResponse.getJSONArray("districts");
                        for(int j =0;j<districts.length();j++)
                        {
                            JSONObject districtResponse = districts.getJSONObject(j);
                            final com.avijit.rms1.data.local.entities.District district ;
                            district = new District(districtResponse.getString("district_id"),divisionResponse.getString("division_id"),districtResponse.getString("district_name"));
                            districtList.add(district);
                            JSONArray areas = districtResponse.getJSONArray("areas");
                            for(int k=0;k<areas.length();k++)
                            {
                                JSONObject areaResponse = areas.getJSONObject(k);
                                final Area area = new Area(district.districtId,areaResponse.getString("area_id"),areaResponse.getString("area"),areaResponse.getString("area_type"));
                                areaList.add(area);
                            }
                        }
                    }
                    final Division[] divisions = new Division[divisionList.size()];
                    final District[] districts = new District[districtList.size()];
                    final Area[] areas = new Area[areaList.size()];
                    for(int i=0;i<divisionList.size();i++)
                    {
                        divisions[i] = divisionList.get(i);
                    }
                    for(int i=0;i<districtList.size();i++)
                    {
                        districts[i] = districtList.get(i);
                    }
                    for(int i=0;i<areaList.size();i++)
                    {
                        areas[i] = areaList.get(i);
                    }
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            AppDatabase db = AppDatabase.getInstance(Nav.this);
                            db.divisionDao().deleteAll();
                            db.districtDao().deleteAll();
                            db.areaDao().deleteAll();

                            db.divisionDao().insertAll(divisions);
                            db.districtDao().insertAll(districts);
                            db.areaDao().insert(areas);
                        }
                    });

                }catch (Exception e){
                    Toast.makeText(Nav.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        },new AppUtils(Nav.this).errorListener);
        stringRequest.setRetryPolicy(AppUtils.STRING_REQUEST_RETRY_POLICY);
        queue.add(stringRequest);
    }

    /**
     * Needed to call the method for checking internet connectivity
     */
    public void broadcastIntent() {
        registerReceiver(myBroadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
    @Override
    protected void onPause() {
        super.onPause();
        //unregisterReceiver(myBroadcastReceiver);
    }
}
