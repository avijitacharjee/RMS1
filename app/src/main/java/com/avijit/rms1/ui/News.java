package com.avijit.rms1.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.avijit.rms1.R;
import com.avijit.rms1.ui.news_fragments.AddNewsFragment;
import com.avijit.rms1.ui.news_fragments.AddNewsSubTypeFragment;
import com.avijit.rms1.ui.news_fragments.AddNewsTypeFragment;
import com.avijit.rms1.ui.news_fragments.NewsHomeFragment;
import com.avijit.rms1.utils.AppUtils;
import com.avijit.rms1.utils.EndDrawerToggle;
import com.google.android.material.navigation.NavigationView;

public class News extends BaseActivity {
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        appUtils = new AppUtils(this);
        initViews();
    }
    private void initViews(){
        //Fragments
        NewsHomeFragment homeFragment = new NewsHomeFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.news_fragment_container,homeFragment);
        ft.commit();
        //toolbar & nav drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("RMS");
        toolbar.setSubtitle("News");
        toolbar.setBackgroundColor(Color.BLACK);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(item->{
            int id = item.getItemId();
            switch (id){
                case R.id.nav_add_news_type : {
                    ft=getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.news_fragment_container,new AddNewsTypeFragment());
                    ft.commit();
                    closeDrawer();
                    break;
                }
                case R.id.nav_news : {
                    ft=getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.news_fragment_container,new NewsHomeFragment());
                    ft.commit();
                    closeDrawer();
                    break;
                }
                case R.id.nav_add_news_sub_type : {
                    ft=getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.news_fragment_container,new AddNewsSubTypeFragment()).commit();
                    closeDrawer();
                    break;
                }
                case R.id.nav_add_news : {
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.news_fragment_container, new AddNewsFragment()).commit();
                    closeDrawer();
                    break;
                }
            }

            return true;
        });
        EndDrawerToggle toggle = new EndDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationOnClickListener(v->super.onBackPressed());
        Menu menu = navigationView.getMenu();
        MenuItem menuItem= menu.findItem(R.id.group_title_1);
        SpannableString s = new SpannableString(menuItem.getTitle());
        s.setSpan(new TextAppearanceSpan(this,R.style.TextAppearance44),0,s.length(),0);
        menuItem.setTitle(s);

    }
    private void closeDrawer(){
        if(drawer.isDrawerOpen(Gravity.RIGHT)){
            drawer.closeDrawer(Gravity.RIGHT);
        }
    }
}
