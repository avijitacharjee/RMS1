package com.avijit.rms1.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.avijit.rms1.R;
import com.avijit.rms1.utils.AppUtils;
import com.avijit.rms1.utils.EndDrawerToggle;
import com.avijit.rms1.viewmodel.AddCompanyViewModel;
import com.google.android.material.navigation.NavigationView;

public class AddCompany extends BaseActivity {
    private EditText companyNameEditText;
    private TextView nextButton;
    private AddCompanyViewModel addCompanyViewModel;
    Animation topAnimation;
    LinearLayout view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);

        initViews();
        appUtils = new AppUtils(this);
        initNavDrawer();
        addCompanyViewModel= ViewModelProviders.of(this).get(AddCompanyViewModel.class);
        addCompanyViewModel.init();
        nextButton.setOnClickListener(v -> {
            if(formIsValid()){
                addCompanyViewModel.addCompany(companyNameEditText.getText().toString()).observe(AddCompany.this, companyStoreResponse -> {
                    Log.d("Observer", "onChanged: "+companyStoreResponse);
                    startActivity(new Intent(AddCompany.this,AddUserInCompany.class));
                });
                Log.d("Observer", "onClick: Called");
            }
        });

        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        view.startAnimation(topAnimation);
    }
    public void initNavDrawer(){
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("RMS");
        toolbar.setSubtitle("Add a company");
        //toolbar.setLogo(R.drawable.ic_exit_to_app_black_24dp);
        toolbar.setBackgroundColor(Color.BLACK);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(appUtils.navigationItemSelectedListener);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        EndDrawerToggle toggle = new EndDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationOnClickListener(v -> AddCompany.super.onBackPressed());
        Log.d("debug", "initNavDrawer: "+Runtime.getRuntime().availableProcessors());
        Menu menu = navigationView.getMenu();
        MenuItem tools= menu.findItem(R.id.group_title_1);
        SpannableString s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s.length(), 0);
        tools.setTitle(s);
    }
    private boolean formIsValid(){
        if(companyNameEditText.getText().toString().equals("")){
            companyNameEditText.setError("Company name can't be empty");
            return false;
        }
        return true;
    }
    private void initViews(){
        companyNameEditText = findViewById(R.id.company_name_edit_text);
        nextButton = findViewById(R.id.next_button);
        view= findViewById(R.id.view);
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(Gravity.RIGHT)){
            drawer.closeDrawer(Gravity.RIGHT);
        }
        else {
            super.onBackPressed();
        }
    }
}
