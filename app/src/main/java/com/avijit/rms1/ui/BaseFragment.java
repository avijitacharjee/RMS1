package com.avijit.rms1.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.avijit.rms1.R;
import com.avijit.rms1.utils.AppUtils;

/**
 * Created by Avijit Acharjee on 6/10/2020 at 12:03 PM.
 * Email: avijitach@gmail.com.
 */
public class BaseFragment extends Fragment {
    protected AppUtils appUtils;
    protected AdapterView.OnItemSelectedListener dropdownItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            setDropdownFirstItemStyle(parent);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    protected void setDropdownFirstItemStyle(AdapterView<?> parent){
        ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.anik2));
        ((TextView) parent.getChildAt(0)).setBackgroundColor(getResources().getColor(R.color.anik1));
        ((TextView) parent.getChildAt(0)).setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
    }
}
