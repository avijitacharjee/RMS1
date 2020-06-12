package com.avijit.rms1.ui.news_fragments;

import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.telephony.gsm.GsmCellLocation;
import android.widget.ImageView;
import android.widget.TextView;

import com.avijit.rms1.R;
import com.avijit.rms1.data.remote.model.News;
import com.avijit.rms1.ui.BaseActivity;
import com.avijit.rms1.utils.AppUtils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class NewsDetails extends BaseActivity {
    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        appUtils = new AppUtils(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("RMS");
        toolbar.setSubtitle("News");
        toolbar.setBackgroundColor(Color.BLACK);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initViews();
        News news;
        try {
            news = new Gson().fromJson(getIntent().getExtras().getString("news"),News.class);
        }catch (Exception e){
            news= new News();
        }
        Picasso.get().load("https://aniksen.me/covidbd/uploads/news/"+news.getNews_image()).into(imageView);
        textView.setText(news.getNews());
    }
    private void initViews(){
        imageView= findViewById(R.id.news_image_view);
        textView = findViewById(R.id.news_text_view);
    }
}
