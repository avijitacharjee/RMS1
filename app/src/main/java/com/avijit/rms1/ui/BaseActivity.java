package com.avijit.rms1.ui;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.avijit.rms1.R;
import com.avijit.rms1.data.remote.model.User;
import com.avijit.rms1.utils.AppUtils;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    protected DrawerLayout drawer;
    protected Toolbar toolbar;
    protected AppUtils appUtils;
    protected void setDropdownFirstItemStyle(AdapterView<?> parent){
        if(parent.getChildAt(0)==null){
            return;
        }
        ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.anik2));
        ((TextView) parent.getChildAt(0)).setBackgroundColor(getResources().getColor(R.color.anik1));
        ((TextView) parent.getChildAt(0)).setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setStatusBarColor(getResources().getColor(R.color.black_1));
        super.onCreate(savedInstanceState);
    }
    protected void setToolbar(){
        toolbar= findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.black_1));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white_1));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white_1));
    }
    protected void setNavDrawer(NavigationView navigationView){
        User user=new User();
        try {
            user = new Gson().fromJson(getSharedPreferences("RMS", MODE_PRIVATE).getString("user", ""), User.class);
        } catch (Exception e) {

        }
        if(user==null){
            return;
        }
        if(user.getTbl_user_types_id()==null){
            return;
        }
        /*if(user.getTbl_user_types_id().equals("1") || user.getTbl_user_types_id().equals("2") || user.getTbl_user_types_id().equals("3")){
            navigationView.getMenu().findItem(R.id.nav_add_company).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_add_user_in_company).setVisible(false);
        }
        if(user.getTbl_user_types_id().equals("1")){
            navigationView.getMenu().findItem(R.id.nav_entry_giving_reliefs).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_add_request_for_relief).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_add_donate_schedule).setVisible(false);
        }
*/
    }

}
