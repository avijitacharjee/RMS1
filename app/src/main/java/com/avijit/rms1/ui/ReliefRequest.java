package com.avijit.rms1.ui;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.avijit.rms1.R;
import com.avijit.rms1.ui.relief_request_fragments.ReliefRequestMainFragment;
import com.avijit.rms1.utils.AppUtils;
import com.avijit.rms1.utils.EndDrawerToggle;
import com.google.android.material.navigation.NavigationView;

public class ReliefRequest extends BaseActivity {
    private EditText fullNameEdit,addressEditText,phoneEditText,familyMemberEditText,earningMemberEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relief_request);
        appUtils = new AppUtils(this);
        initViews();
        //viewModel.init();
        initNavDrawer();
        /**
         * Fragments
         */
        ReliefRequestMainFragment firstFragment = new ReliefRequestMainFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.relief_request_fragment_container,firstFragment);
        ft.commit();
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        }*/
    }
    private void initViews(){
       /* fullNameEdit = findViewById(R.id.full_name_edit_text);
        addressEditText = findViewById(R.id.address_edit_text);
        phoneEditText = findViewById(R.id.phone_edit_text);
        familyMemberEditText = findViewById(R.id.family_members_edit_text);
        earningMemberEditText = findViewById(R.id.earning_members_edit_text);*/
    }
    public void initNavDrawer(){

        setToolbar();
        toolbar.setTitle("RMS");
        toolbar.setSubtitle("Add relief request");
        //toolbar.setLogo(R.drawable.ic_exit_to_app_black_24dp);
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
        toolbar.setNavigationOnClickListener(v->super.onBackPressed());
        Log.d("debug", "initNavDrawer: "+Runtime.getRuntime().availableProcessors());
        Menu menu = navigationView.getMenu();
        MenuItem tools= menu.findItem(R.id.group_title_1);
        SpannableString s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s.length(), 0);
        tools.setTitle(s);
        setNavDrawer(navigationView);
    }
}
