package com.avijit.rms1.ui;

import androidx.annotation.MainThread;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.provider.FontsContractCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.avijit.rms1.R;
import com.avijit.rms1.data.local.entities.Area;
import com.avijit.rms1.data.local.entities.District;
import com.avijit.rms1.data.local.entities.Division;
import com.avijit.rms1.utils.AppUtils;
import com.avijit.rms1.viewmodel.FamilyAddressViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FamilyAddress extends BaseActivity {
    FamilyAddressViewModel viewModel;
    TextView nextButton;

    Spinner divisionSpinner,districtSpinner,typeSpinner,areaSpinner;
    EditText addressEditText;

    ProgressBar progressBar;
    ProgressDialog progressDialog;


    private final List<Division> divisionList = new ArrayList<>();
    private List<District> districtList = new ArrayList<>();
    private List<String> typeList = new ArrayList<>();
    private List<Area> areaList = new ArrayList<>();
    private List<Area> selectedArea = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_address);
        viewModel = ViewModelProviders.of(this).get(FamilyAddressViewModel.class);

        divisionSpinner= findViewById(R.id.division_spinner);
        districtSpinner = findViewById(R.id.district_spinner);
        typeSpinner = findViewById(R.id.type_spinner);
        areaSpinner = findViewById(R.id.area_spinner);
        addressEditText= findViewById(R.id.address_edit_text);
        nextButton = findViewById(R.id.next_button);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("RMS");
        getSupportActionBar().setSubtitle("Entry Given Reliefs");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,new String[]{"--Select Division--"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        divisionSpinner.setAdapter(adapter);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,new String[]{"--Select District--"});
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        districtSpinner.setAdapter(adapter1);

        divisionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setDropdownFirstItemStyle(parent);
                if(position>0){
                    setDistricts();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        viewModel.getDivisions().observe(this, new Observer<List<Division>>() {
            @Override
            public void onChanged(List<Division> divisions) {
                divisionList.clear();
                divisionList.addAll(divisions);
                String[] divs = new String[divisionList.size() + 1];
                divs[0] = "--Select Division--";
                for (int i = 0; i < divisionList.size(); i++) {
                    divs[i + 1] = divisionList.get(i).name;
                }
                final ArrayAdapter<String> adapter = new ArrayAdapter<>(FamilyAddress.this, android.R.layout.simple_spinner_dropdown_item, divs);
                divisionSpinner.setAdapter(adapter);

            }
        });
        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setDropdownFirstItemStyle(parent);
                if(position>0){
                    setTypes();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,new String[]{"--Select type--"});
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter2);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setDropdownFirstItemStyle(parent);
                if(position>0){
                    setAreas();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,new String[]{"--Select area--"});
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        areaSpinner.setAdapter(adapter3);
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

                if(formValidationPassed())
                {
                    Intent familyRegistration = new Intent(getApplicationContext(),StoreRelief.class);
                    //ids
                    familyRegistration.putExtra("divisionId",divisionList.get(divisionSpinner.getSelectedItemPosition()-1).divisionId);
                    familyRegistration.putExtra("districtId",districtList.get(districtSpinner.getSelectedItemPosition()-1).districtId);
                    familyRegistration.putExtra("address",addressEditText.getText().toString());
                    familyRegistration.putExtra("areaId",selectedArea.get(areaSpinner.getSelectedItemPosition()-1).areaId);
                    //Original values
                    familyRegistration.putExtra("division",divisionSpinner.getSelectedItem().toString());
                    familyRegistration.putExtra("district",districtSpinner.getSelectedItem().toString());
                    familyRegistration.putExtra("area",areaSpinner.getSelectedItem().toString());
                    startActivity(familyRegistration);
                }
                else
                {
                    //  Toast.makeText(FamilyAddress.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    Toast toast = Toast.makeText(getApplicationContext(),"All fields are required" , Toast.LENGTH_SHORT);
                    View view = toast.getView();

                    //Gets the actual oval background of the Toast then sets the colour filter
                    view.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

                    //Gets the TextView from the Toast so it can be editted
                    TextView text = view.findViewById(android.R.id.message);
                    text.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                    text.setTextColor(Color.WHITE);

                    toast.show();
                }
            }
        });
    }

    private void setDistricts() {
        if (divisionSpinner.getSelectedItemPosition() <1) {
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(FamilyAddress.this, android.R.layout.simple_spinner_dropdown_item, new String[]{"--Select District--"});
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            districtSpinner.setAdapter(adapter);
            return;
        }
        viewModel.getDistrictByDivisionId(divisionList.get(divisionSpinner.getSelectedItemPosition() - 1).divisionId).observe(this, new Observer<List<District>>() {
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
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(FamilyAddress.this, android.R.layout.simple_spinner_dropdown_item, districts);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                districtSpinner.setAdapter(adapter);
            }
        });
    }

    private void setTypes() {
        if(districtSpinner.getSelectedItemPosition()<1){
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(FamilyAddress.this,  android.R.layout.simple_spinner_dropdown_item, new String[]{"--Select Type--"});
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            typeSpinner.setAdapter(adapter);
            return;
        }
        viewModel.getAreasByDistrictId(districtList.get(districtSpinner.getSelectedItemPosition() - 1).districtId).observe(this, new Observer<List<Area>>() {
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
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(FamilyAddress.this,  android.R.layout.simple_spinner_dropdown_item, types);
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
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(FamilyAddress.this, R.layout.spinner_layout, areas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                areaSpinner.setAdapter(adapter);
            }
        });
    }


    public boolean onOptionsItemSelected(MenuItem item){
        super.onBackPressed();
        return true;
    }
    private boolean formValidationPassed() {

        boolean flag = true;
        if(divisionSpinner.getSelectedItemPosition()==0)
        {
            flag=false;
        }
        if(districtSpinner.getSelectedItemPosition()==0)
        {
            flag=false;
        }
        if(typeSpinner.getSelectedItemPosition()==0)
        {
            flag=false;
        }
        if(areaSpinner.getSelectedItemPosition()==0){
            flag=false;
        }
        if(addressEditText.length()==0)
        {
            flag=false;
        }
        return flag;
    }
}