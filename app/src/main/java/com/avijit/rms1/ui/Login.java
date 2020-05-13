package com.avijit.rms1.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avijit.rms1.R;
import com.avijit.rms1.data.remote.model.AuthBody;
import com.avijit.rms1.data.remote.model.User;
import com.avijit.rms1.data.remote.responses.AuthResponse;
import com.avijit.rms1.viewmodel.LoginViewModel;

public class Login extends AppCompatActivity {
    LoginViewModel loginViewModel;
    EditText userNameEditText,passwordEditText;
    TextView loginButton;
    Button signUpIntentButton;
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
        });
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
        Intent intent = new Intent(Login.this,Nav.class);
        startActivity(intent);
        /*finish();
        overridePendingTransition(0,0);*/
    }
}
