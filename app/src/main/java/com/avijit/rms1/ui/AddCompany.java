package com.avijit.rms1.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.avijit.rms1.R;

public class AddCompany extends AppCompatActivity {
    EditText companyNameEditText;
    TextView nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);
        initViews();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void initViews(){
        companyNameEditText = findViewById(R.id.company_name_edit_text);
        nextButton = findViewById(R.id.next_button);
    }
}
