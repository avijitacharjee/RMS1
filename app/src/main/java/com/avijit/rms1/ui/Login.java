package com.avijit.rms1.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.avijit.rms1.data.remote.model.AuthBody;
import com.avijit.rms1.data.remote.model.User;
import com.avijit.rms1.data.remote.responses.AuthResponse;
import com.avijit.rms1.utils.AppUtils;
import com.avijit.rms1.viewmodel.LoginViewModel;

import java.util.HashMap;
import java.util.Map;

public class Login extends BaseActivity {
    LoginViewModel loginViewModel;
    EditText userNameEditText,passwordEditText;
    TextView loginButton;
    TextView signUpIntentButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userNameEditText = findViewById(R.id.user_name_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        loginButton = findViewById(R.id.login_button);
        signUpIntentButton = findViewById(R.id.signup_intent_button);

        loginViewModel= ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.init();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFormValid()){
                    final AuthBody user = new AuthBody();
                    user.setUsername(userNameEditText.getText().toString());
                    user.setPassword(passwordEditText.getText().toString());
                    loginViewModel.getAuth(user).observe(Login.this, new Observer<AuthResponse>() {
                        @Override
                        public void onChanged(AuthResponse response) {
                            if(response.isUser()){
                                getSharedPreferences("RMS",MODE_PRIVATE).edit().putString("token",response.getAccessToken()).apply();
                                startActivity(new Intent(Login.this,Nav.class));
                            }
                            else {
                                Toast.makeText(Login.this, "Incorrect username/password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
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
        saveUserInfo();
        signUpIntentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,SignUp.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Login.this,MainDashboard.class);
        startActivity(intent);
        /*finish();
        overridePendingTransition(0,0);*/
    }
    private boolean isFormValid(){
        boolean valid = true;
        if(userNameEditText.getText().toString().isEmpty()){
            valid = false;
        }
        if(passwordEditText.getText().toString().isEmpty()){
            valid = false;
        }
        return valid;
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
                Toast.makeText(Login.this, error.toString(), Toast.LENGTH_SHORT).show();
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
}
