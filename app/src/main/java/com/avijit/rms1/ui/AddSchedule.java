package com.avijit.rms1.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.avijit.rms1.R;
import com.avijit.rms1.data.local.AppDatabase;
import com.avijit.rms1.data.local.entities.Area;
import com.avijit.rms1.data.local.entities.District;
import com.avijit.rms1.data.local.entities.Division;
import com.avijit.rms1.data.remote.model.ReliefSchedule;
import com.avijit.rms1.data.remote.responses.ReliefScheduleStoreResponse;
import com.avijit.rms1.utils.AppUtils;
import com.avijit.rms1.utils.EndDrawerToggle;
import com.avijit.rms1.viewmodel.AddScheduleVIewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddSchedule extends BaseActivity {
    AddScheduleVIewModel addScheduleVIewModel;
    AlertDialog dialog;
    DatePickerDialog datePickerDialog;
    TextView dateEditText;
    Spinner divisionSpinner, districtSpinner, typeSpinner, areaSpinner;
    EditText addressEditText;
    TextView nextButton;
    DrawerLayout drawer;

    private final List<Division> divisionList = new ArrayList<>();
    private List<District> districtList = new ArrayList<>();
    private List<String> typeList = new ArrayList<>();
    private List<Area> areaList = new ArrayList<>();

    /**
     * List of areas after selecting type
     */
    private List<Area> selectedArea = new ArrayList<>();

    /**
     * @param savedInstanceState previously saved bundle
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        setToolbar();
        dateEditText = findViewById(R.id.date_picker);
        AppUtils utils = new AppUtils(this);
        dialog = new AppUtils(this).dialog;
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(AddSchedule.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                dateEditText.setText(year + "-"
                                        + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        addScheduleVIewModel = ViewModelProviders.of(this).get(AddScheduleVIewModel.class);
        toolbar.setTitle("RMS");
        toolbar.setSubtitle("Add a schedule for giving relief");
        //toolbar.setLogo(R.drawable.ic_exit_to_app_black_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(utils.navigationItemSelectedListener);
        EndDrawerToggle toggle = new EndDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AddSchedule.super.onBackPressed();
            }
        });

        Menu menu = navigationView.getMenu();
        MenuItem tools = menu.findItem(R.id.group_title_1);
        SpannableString s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s.length(), 0);
        tools.setTitle(s);


        divisionSpinner = findViewById(R.id.division_spinner);
        districtSpinner = findViewById(R.id.district_spinner);
        typeSpinner = findViewById(R.id.type_spinner);
        areaSpinner = findViewById(R.id.area_spinner);
        addressEditText = findViewById(R.id.address_edit_text);
        nextButton = findViewById(R.id.next_button);
        addScheduleVIewModel.getDivisions().observe(this, new Observer<List<Division>>() {
            @Override
            public void onChanged(List<Division> divisions) {
                divisionList.clear();
                divisionList.addAll(divisions);
                String[] divs = new String[divisionList.size() + 1];
                divs[0] = "--Select Division--";
                for (int i = 0; i < divisionList.size(); i++) {
                    divs[i + 1] = divisionList.get(i).name;
                }
                final ArrayAdapter<String> adapter = new ArrayAdapter<>(AddSchedule.this, android.R.layout.simple_spinner_dropdown_item, divs);
                divisionSpinner.setAdapter(adapter);
            }
        });
        setDistricts();
        setTypes();
        setAreas();
        divisionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setDropdownFirstItemStyle(parent);
                if (position > 0) {
                    setDistricts();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setDropdownFirstItemStyle(parent);
                if (position > 0) {
                    setTypes();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setDropdownFirstItemStyle(parent);
                if (position > 0) {
                    setAreas();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setDropdownFirstItemStyle(parent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFormValid()) {
                    ReliefSchedule reliefSchedule = new ReliefSchedule();
                    reliefSchedule.setAddress(addressEditText.getText().toString());
                    reliefSchedule.setCompany_id("1");
                    reliefSchedule.setUser_id("1");
                    reliefSchedule.setSchedule_date(dateEditText.getText().toString());
                    reliefSchedule.setDistrict_id(districtList.get(districtSpinner.getSelectedItemPosition() - 1).districtId);
                    reliefSchedule.setDivision_id(divisionList.get(divisionSpinner.getSelectedItemPosition() - 1).divisionId);
                    reliefSchedule.setArea_id(areaList.get(areaSpinner.getSelectedItemPosition() - 1).areaId);
                    Log.d("JSON", "onClick: " + new Gson().toJson(reliefSchedule));
                    dialog = utils.dialog;
                    dialog.show();
                    addScheduleVIewModel.addSchedule(reliefSchedule).observe(AddSchedule.this, new Observer<ReliefScheduleStoreResponse>() {
                        @Override
                        public void onChanged(ReliefScheduleStoreResponse reliefScheduleStoreResponse) {
                            dialog.dismiss();
                            Log.d("TAG", "onChanged: " + new Gson().toJson(reliefScheduleStoreResponse));
                            if (reliefScheduleStoreResponse.isNetworkIsSuccessful()){
                                Toast.makeText(AddSchedule.this, reliefScheduleStoreResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                clearFields();
                            }
                            else {
                                Toast.makeText(AddSchedule.this, "Failed to connect", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT);
                    View view = toast.getView();

                    //Gets the actual oval background of the Toast then sets the colour filter
                    view.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

                    //Gets the TextView from the Toast so it can be editted
                    TextView text = view.findViewById(android.R.id.message);
                    text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    text.setTextColor(Color.WHITE);

                    toast.show();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawer(Gravity.RIGHT);
        } else {
            super.onBackPressed();
        }
    }

    private void setDistricts() {
        if (divisionSpinner.getSelectedItemPosition() < 1) {
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddSchedule.this, R.layout.spinner_layout, new String[]{"--Select District--"});
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            districtSpinner.setAdapter(adapter);
            return;
        }
        addScheduleVIewModel.getDistrictByDivisionId(divisionList.get(divisionSpinner.getSelectedItemPosition() - 1).divisionId).observe(this, new Observer<List<District>>() {
            @Override
            public void onChanged(List<District> districtss) {
                String[] districts = new String[1];
                districts[0] = "--Select Districts--";
                if (divisionSpinner.getSelectedItemPosition() > 0) {
                    districtList = districtss;
                    districts = new String[districtList.size() + 1];
                    districts[0] = "--Select Districts";
                    for (int i = 0; i < districtList.size(); i++) {
                        districts[i + 1] = districtList.get(i).name;
                    }
                }
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddSchedule.this, R.layout.spinner_layout, districts);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                districtSpinner.setAdapter(adapter);
            }
        });
    }

    private void setTypes() {
        if (districtSpinner.getSelectedItemPosition() < 1) {
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddSchedule.this, R.layout.spinner_layout, new String[]{"--Select Type--"});
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            typeSpinner.setAdapter(adapter);
            return;
        }
        addScheduleVIewModel.getAreasByDistrictId(districtList.get(districtSpinner.getSelectedItemPosition() - 1).districtId).observe(this, new Observer<List<Area>>() {
            @Override
            public void onChanged(List<Area> areas) {
                String[] types = new String[1];
                types[0] = "--Select Types--";
                if (districtSpinner.getSelectedItemPosition() > 0) {
                    areaList = areas;
                    for (int i = 0; i < areaList.size(); i++) {
                        //districts[i+1]=districtList.get(i).name;
                        Area area = areaList.get(i);
                        if (!typeList.contains(area.type)) {
                            typeList.add(area.type);
                        }
                    }
                    types = new String[typeList.size() + 1];
                    types[0] = "--Select Types--";
                    for (int i = 0; i < typeList.size(); i++) {
                        types[i + 1] = typeList.get(i);
                    }
                }
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddSchedule.this, R.layout.spinner_layout, types);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                typeSpinner.setAdapter(adapter);
            }
        });
    }

    private void setAreas() {
        selectedArea.clear();
        for (int i = 0; i < areaList.size(); i++) {
            if (areaList.get(i).type.equals(typeList.get(typeSpinner.getSelectedItemPosition() - 1))) {
                selectedArea.add(areaList.get(i));
            }
        }
        String[] areas = new String[1];
        areas[0] = "--Select Area--";
        if (selectedArea.size() > 0) {
            areas = new String[selectedArea.size() + 1];
            areas[0] = "--Select Area--";
            for (int i = 0; i < selectedArea.size(); i++) {
                areas[i + 1] = selectedArea.get(i).name;
            }
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddSchedule.this, R.layout.spinner_layout, areas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                areaSpinner.setAdapter(adapter);
            }
        });
    }

    private boolean isFormValid() {
        boolean valid = true;
        if (divisionSpinner.getSelectedItemPosition() == 0) {
            valid = false;
        }
        if (districtSpinner.getSelectedItemPosition() == 0) {
            valid = false;
        }
        if (areaSpinner.getSelectedItemPosition() == 0) {
            valid = false;
        }
        if (dateEditText.getText().toString().contains("D")) {
            valid = false;
        }
        if (addressEditText.getText().toString().isEmpty()) {
            valid = false;
        }
        return valid;
    }

    public void setLocations() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://aniksen.me/covidbd/api/locations";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    final AppDatabase db = AppDatabase.getInstance(AddSchedule.this);

                    List<Division> divisionList = new ArrayList<>();
                    List<District> districtList = new ArrayList<>();
                    List<Area> areaList = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray locations = jsonObject.getJSONArray("locations");
                    for (int i = 0; i < locations.length(); i++) {
                        JSONObject divisionResponse = locations.getJSONObject(i);
                        final Division division = new Division(divisionResponse.getString("division_id"), divisionResponse.getString("division_name"));
                        divisionList.add(division);
                        JSONArray districts = divisionResponse.getJSONArray("districts");
                        for (int j = 0; j < districts.length(); j++) {
                            JSONObject districtResponse = districts.getJSONObject(j);
                            final com.avijit.rms1.data.local.entities.District district;
                            district = new District(districtResponse.getString("district_id"), divisionResponse.getString("division_id"), districtResponse.getString("district_name"));
                            districtList.add(district);
                            JSONArray areas = districtResponse.getJSONArray("areas");
                            for (int k = 0; k < areas.length(); k++) {
                                JSONObject areaResponse = areas.getJSONObject(k);
                                final Area area = new Area(district.districtId, areaResponse.getString("area_id"), areaResponse.getString("area"), areaResponse.getString("area_type"));
                                areaList.add(area);
                            }
                        }
                    }
                    final Division[] divisions = new Division[divisionList.size()];
                    final District[] districts = new District[districtList.size()];
                    final Area[] areas = new Area[areaList.size()];
                    for (int i = 0; i < divisionList.size(); i++) {
                        divisions[i] = divisionList.get(i);
                    }
                    for (int i = 0; i < districtList.size(); i++) {
                        districts[i] = districtList.get(i);
                    }
                    for (int i = 0; i < areaList.size(); i++) {
                        areas[i] = areaList.get(i);
                    }
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            AppDatabase db = AppDatabase.getInstance(AddSchedule.this);
                            db.divisionDao().deleteAll();
                            db.districtDao().deleteAll();
                            db.areaDao().deleteAll();

                            db.divisionDao().insertAll(divisions);
                            db.districtDao().insertAll(districts);
                            db.areaDao().insert(areas);
                        }
                    });

                } catch (Exception e) {
                    Toast.makeText(AddSchedule.this, "" + e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new AppUtils(AddSchedule.this).errorListener);
        stringRequest.setRetryPolicy(AppUtils.STRING_REQUEST_RETRY_POLICY);
        queue.add(stringRequest);
    }
    private void clearFields()
    {
        addressEditText.setText("");
        dateEditText.setText("");
    }
}
