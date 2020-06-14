package com.avijit.rms1.ui;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.TypedValue;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.avijit.rms1.R;
import com.avijit.rms1.utils.AppUtils;

public class BaseActivity extends AppCompatActivity {
    protected DrawerLayout drawer;
    protected Toolbar toolbar;
    protected AppUtils appUtils;
    protected void setDropdownFirstItemStyle(AdapterView<?> parent){
        ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.anik2));
        ((TextView) parent.getChildAt(0)).setBackgroundColor(getResources().getColor(R.color.anik1));
        ((TextView) parent.getChildAt(0)).setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
    }
}
