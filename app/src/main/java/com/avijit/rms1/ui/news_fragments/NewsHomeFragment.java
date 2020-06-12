package com.avijit.rms1.ui.news_fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.avijit.rms1.R;
import com.avijit.rms1.adapters.NewsFragmentPagerAdapter;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.avijit.rms1.generated.callback.OnClickListener;
import com.avijit.rms1.ui.News;
import com.avijit.rms1.viewmodel.NewsDynamicFragmentViewModel;
import com.avijit.rms1.viewmodel.NewsHomeFragmentViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Avijit Acharjee on 6/7/2020 at 7:40 PM.
 * Email: avijitach@gmail.com.
 */
public class NewsHomeFragment extends Fragment {
    NewsDynamicFragmentViewModel viewModel;
    private static final String TAG = "NewsHomeFragment";
    BottomNavigationView bottomNavigationView;
    ArrayList<String> tabTitle = new ArrayList<>();
    private TabLayout tab;

    private ViewPager viewPager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_home,null,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(view);
        viewModel= ViewModelProviders.of(this).get(NewsDynamicFragmentViewModel.class);
        viewModel.setOnClickListener(new View.OnClickListener(){

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),  "kjh", Toast.LENGTH_SHORT).show();
                AddNewsFragment otherFragment = new AddNewsFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.news_home_container, otherFragment);
                ft.commit();
            }
        });
       /* Menu menu = bottomNavigationView.getMenu();
        menu.add(Menu.NONE, 9283487, Menu.NONE, "HI");
        menu.add(Menu.NONE, 9283487, Menu.NONE, "Hello");
        menu.add(Menu.NONE, 9283487, Menu.NONE, "Oda");
        menu.add(Menu.NONE, 9283487, Menu.NONE, "Oda");
        menu.add(Menu.NONE, 9283487, Menu.NONE, "Oda");
        menu.add(Menu.NONE, 9283487, Menu.NONE, "Oda");
        menu.add(Menu.NONE, 9283487, Menu.NONE, "Oda");
        menu.add(Menu.NONE, 9283487, Menu.NONE, "Oda");
        menu.add(Menu.NONE, 9283487, Menu.NONE, "Oda");
        menu.add(Menu.NONE, 9283487, Menu.NONE, "Oda");
        menu.add(Menu.NONE, 9283487, Menu.NONE, "Oda");
        menu.add(Menu.NONE, 9283487, Menu.NONE, "Oda");
        menu.add(Menu.NONE, 9283487, Menu.NONE, "Oda");
        menu.add(Menu.NONE, 9283487, Menu.NONE, "Oda");
        menu.add(Menu.NONE, 9283487, Menu.NONE, "Oda");
        menu.add(Menu.NONE, 9283487, Menu.NONE, "Oda");
        menu.add(Menu.NONE, 9283487, Menu.NONE, "Oda");*/
        tabTitle.clear();
        for (int k = 0; k <10; k++) {
            tab.addTab(tab.newTab().setText("Pitch-" + k));
            tabTitle.add("P - " + k);
        }
        NewsFragmentPagerAdapter adapter = new NewsFragmentPagerAdapter(getActivity().getSupportFragmentManager(), tab.getTabCount(), tabTitle);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        tab.setupWithViewPager(viewPager);
//Bonus Code : If your tab layout has more than 2 tabs then tab will scroll other wise they will take whole width of the screen
        if (tab.getTabCount() == 2) {
            tab.setTabMode(TabLayout.MODE_FIXED);
        } else {
            tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        }

    }

    private void initViews(View view){
        //bottomNavigationView = view.findViewById(R.id.bottom_nav_view);
        tab=view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.news_home_container);
    }
    public View.OnClickListener getListener(){
            return new View.OnClickListener(){

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                AddNewsFragment otherFragment = new AddNewsFragment();
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.news_home_container, otherFragment);
                ft.commit();
            }
        };
    }

}
