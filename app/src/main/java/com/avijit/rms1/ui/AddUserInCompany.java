package com.avijit.rms1.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.avijit.rms1.R;
import com.avijit.rms1.data.remote.model.Company;
import com.avijit.rms1.data.remote.model.CompanyUser;
import com.avijit.rms1.data.remote.model.User;
import com.avijit.rms1.data.remote.responses.CompanyUserStoreResponse;
import com.avijit.rms1.utils.AppUtils;
import com.avijit.rms1.viewmodel.AddUserInCompanyViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class AddUserInCompany extends BaseActivity {
    private static final String TAG = "AddUserInCompany";
    AddUserInCompanyViewModel addUserInCompanyViewModel;
    Spinner userTypeSpinner,typeSpinner;
    EditText emailEditText,fullNameEditText,phoneNoEditText,nidEditText,passwordEditText,confirmPasswordEditText;
    List<String> types;
    List<String> userTypes;
    LinearLayout newUserLayout;
    TextView goButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_in_company);
        addUserInCompanyViewModel = ViewModelProviders.of(this).get(AddUserInCompanyViewModel.class);
        addUserInCompanyViewModel.init();
        appUtils = new AppUtils(this);
        initViews();
        if(getSharedPreferences("RMS",MODE_PRIVATE).getString("company","").equals("")){
            startActivity(new Intent(this,AddCompany.class));
            Toast.makeText(this, "Please make a company first", Toast.LENGTH_SHORT).show();
        }

        userTypes = new ArrayList<>();
        userTypes.add("--Select user type--");
        userTypes.add("New User");
        userTypes.add("Registered User");
        final ArrayAdapter<String> userTypeArrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,userTypes);
        userTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeSpinner.setAdapter(userTypeArrayAdapter);

        userTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black_1));
                    ((TextView) parent.getChildAt(0)).setBackgroundColor(getResources().getColor(R.color.white_1));
                }catch (Exception e) {}
                if(position==1){
                    newUserLayout.setVisibility(View.VISIBLE);
                }
                if(position==2){
                    newUserLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        types = new ArrayList<>();
        types.add("--Select type--");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,types);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter2);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black_1));
                    ((TextView) parent.getChildAt(0)).setBackgroundColor(getResources().getColor(R.color.white_1));
                }catch (Exception e) {}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        goButton.setOnClickListener(v -> {
            if(true){
                CompanyUser companyUser = new CompanyUser();
                String companyId= new Gson().fromJson(getSharedPreferences("RMS",MODE_PRIVATE).getString("company",""), Company.class).getId()+"";
                companyUser.setCompany_id(companyId);
                companyUser.setRole("2");
                companyUser.setUser_id(emailEditText.getText().toString());
                if(userTypeSpinner.getSelectedItemPosition()==2){
                    appUtils.dialog.show();
                    addUserInCompanyViewModel.addUser(emailEditText.getText().toString(),companyId,"2").observe(AddUserInCompany.this, response -> {
                        appUtils.dialog.dismiss();
                        if(response.isNetworkIsSuccessful()){
                            Toast.makeText(AddUserInCompany.this, "User Added Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();
                        }

                    });

                }
                else if(userTypeSpinner.getSelectedItemPosition()==2){
                    User user = new User();
                    user.setName(fullNameEditText.getText().toString());
                    user.setEmail(emailEditText.getText().toString());
                    user.setNid(nidEditText.getText().toString());
                    user.setPhone(phoneNoEditText.getText().toString());
                    user.setPassword(passwordEditText.getText().toString());
                    Log.d(TAG, "onClick: "+user.toString());
                    addUserInCompanyViewModel.registerNewUser(user).observe(this,response->{
                        if(response.isNetworkIsSuccessful()){
                            addUserInCompanyViewModel.addUser(user.getEmail(),companyId,"2");
                            Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }
    private void initViews(){
        userTypeSpinner = findViewById(R.id.user_type_spinner);
        emailEditText  = findViewById(R.id.email_edit_text);
        fullNameEditText = findViewById(R.id.full_name_edit_text);
        phoneNoEditText = findViewById(R.id.phone_edit_text);
        nidEditText = findViewById(R.id.nid_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        newUserLayout = findViewById(R.id.new_user_layout);
        goButton = findViewById(R.id.go);
        typeSpinner = findViewById(R.id.type_spinner);
    }
    private boolean form1Valid(){
        boolean valid = true;
        return valid;
    }
    private boolean formIsValid(){
        boolean valid= true;
        if(userTypeSpinner.getSelectedItemPosition()==1){
            return !emailEditText.getText().toString().isEmpty();
        }
        if(emailEditText.getText().toString().isEmpty()){
            valid = false;
        }
        if(fullNameEditText.getText().toString().isEmpty()){
            valid=false;
        }
        if(phoneNoEditText.getText().toString().isEmpty()){
            valid = false;
        }
        if(nidEditText.getText().toString().isEmpty()){
            valid = false;
        }
        if(passwordEditText.getText().toString().isEmpty()){
            valid = false;
        }
        if(confirmPasswordEditText.getText().toString().isEmpty()){
            valid = false;
        }
        if(passwordEditText.getText().toString().equals(confirmPasswordEditText.getText().toString())){
            confirmPasswordEditText.setError("Passwords didn't match");
        }
        return valid;
    }
}
