package com.avijit.rms1.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.avijit.rms1.R;
import com.avijit.rms1.data.remote.model.User;
import com.avijit.rms1.data.remote.responses.UserStoreResponse;
import com.avijit.rms1.data.remote.responses.UserTypeResponse;
import com.avijit.rms1.viewmodel.SignUpViewModel;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends BaseActivity {
    SignUpViewModel viewModel;

    ProgressDialog progressDialog;
    TextView loginIntentButton;
    TextView goButton;
    ImageView logoImage;
    TextInputLayout tran2,tran3;
    Spinner typeSpinner;
    String types[] = new String[]{"As Individual","As Thana User","As Company"};
    String typeIds[];
    EditText nameEditText,emailEditText,phoneEditText,nidEditText,passwordEditText,confirmPasswordEditText;

    private static final String TAG = "SignUp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        loginIntentButton = findViewById(R.id.signup_intent_button);
        goButton = findViewById(R.id.go);
        logoImage = findViewById(R.id.logo_image);
        tran2 = findViewById(R.id.username);
        tran3 = findViewById(R.id.password);
        typeSpinner = findViewById(R.id.type_spinner);
        viewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        initEditTexts();
        setTypeSpinner();
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,types);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter2);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                    ((TextView) parent.getChildAt(0)).setBackgroundColor(Color.WHITE);
                }catch (Exception e) {}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Log.d("RMS", "onCreate: Abc");

        loginIntentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                Pair[] pairs = new Pair[5];
                pairs[1]= new Pair<View,String>(loginIntentButton,"tran0");
                pairs[0]= new Pair<View,String>(logoImage,"tran1");
                pairs[2]= new Pair<View,String>(tran2,"tran2");
                pairs[3]= new Pair<View,String>(tran3,"tran3");
                pairs[4]= new Pair<View,String>(goButton,"tran4");
                ActivityOptions options = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this,pairs);
                }
                startActivity(intent,options.toBundle());
            }
        });
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(formIsValid())
                {
                    v();
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(),"Please check all fields" , Toast.LENGTH_SHORT);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SignUp.this,MainDashboard.class);
        startActivity(intent);
        /*finish();
        overridePendingTransition(0,0);*/
    }
    /*
     * Volley request for registration
     * @Params no params
     */
    public void setTypeSpinner(){
        viewModel.getUserTypes().observe(this, new Observer<UserTypeResponse>() {
            @Override
            public void onChanged(UserTypeResponse userTypeResponse) {
                String[] t = new String[userTypeResponse.getData().size()+1];
                t[0]="--Select User Type--";
                typeIds = new String[userTypeResponse.getData().size()];
                for(int i=0;i<userTypeResponse.getData().size();i++)
                {
                    t[i+1] = userTypeResponse.getData().get(i).getName();
                    typeIds[i]=userTypeResponse.getData().get(i).getId();
                }
                types=t;
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(SignUp.this,android.R.layout.simple_spinner_dropdown_item,types);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                typeSpinner.setAdapter(adapter2);
            }
        });
    }

    /*
     * Volley request for registration
     * @Params no params
     */
    public void v() {
        User user = new User();
        user.setName(nameEditText.getText().toString());
        user.setEmail(emailEditText.getText().toString());
        user.setPhone(phoneEditText.getText().toString());
        user.setNid(nidEditText.getText().toString());
        user.setPassword(passwordEditText.getText().toString());
        user.setTbl_user_types_id(typeIds[typeSpinner.getSelectedItemPosition()-1]);

        viewModel.saveUser(user).observe(this, new Observer<UserStoreResponse>() {
            @Override
            public void onChanged(UserStoreResponse userStoreResponse) {
                Log.d(TAG, "onChanged: "+userStoreResponse.toString());
            }
        });
    }

    /*
     * initializes all editTexts
     * @Params no params
     */
    private void initEditTexts()
    {
        nameEditText = findViewById(R.id.full_name_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        phoneEditText = findViewById(R.id.phone_edit_text);
        nidEditText = findViewById(R.id.nid_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
    }

    private boolean formIsValid()
    {
        boolean valid = true;
        if(nameEditText.getText().toString().equals(""))
        {
            valid = false;
        }
        if(emailEditText.getText().toString().equals(""))
        {
            valid = false;
        }
        if(phoneEditText.getText().toString().equals(""))
        {
            valid = false;
        }
        if(nidEditText.getText().toString().equals(""))
        {
            valid = false;
        }
        if(passwordEditText.getText().toString().equals(""))
        {
            valid = false;
        }
        if(confirmPasswordEditText.getText().toString().equals(""))
        {
            valid = false;
        }
        if(!confirmPasswordEditText.getText().toString().equals(passwordEditText.getText().toString()))
        {
            confirmPasswordEditText.setError("Passwords doesn't matched");
            valid = false;
        }
        return valid;
    }
}

