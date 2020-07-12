package com.avijit.rms1.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.avijit.rms1.R;
import com.avijit.rms1.data.remote.model.User;
import com.google.gson.Gson;

public class Profile extends BaseActivity {
    TextView nameTextView, emailTextView, phoneTextView, nidTextView, typeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initViews();
        User user;
        try {
            user = new Gson().fromJson(getSharedPreferences("RMS", MODE_PRIVATE).getString("user", ""), User.class);
        } catch (Exception e) {
            User user1 = new User();
            user = user1;
        }
        nameTextView.setText(user.getName());
        emailTextView.setText(user.getEmail());
        phoneTextView.setText(user.getPhone() == null ? "Not provided" : user.getPhone());
        nidTextView.setText(user.getNid());
        typeTextView.setText(getTypeFromId(user.getTbl_user_types_id()));

    }

    private void initViews() {
        nameTextView = findViewById(R.id.name_text_view);
        emailTextView = findViewById(R.id.email_text_view);
        phoneTextView = findViewById(R.id.phone_text_view);
        nidTextView = findViewById(R.id.nid_text_view);
        typeTextView = findViewById(R.id.user_type_text_view);
    }

    //tbl_user_types_id
    private String getTypeFromId(String id) {
        if (id.equals("1")) {
            return "Journalist";
        } else if (id.equals("2")) {
            return "Individual";
        } else if (id.equals("3")) {
            return "City Corporation";
        } else if (id.equals("4")){
            return "Company";
        }
        return "";
    }
}
