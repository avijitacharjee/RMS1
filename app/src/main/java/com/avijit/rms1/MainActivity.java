package com.avijit.rms1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.avijit.rms1.ui.MainDashboard;
import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    static int SPLASH_TIME = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView) findViewById(R.id.gif_image);
        Glide.with(this).asGif().load(R.drawable.stay_home).into(imageView);
        splash();
    }
    public void splash()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splashIntent= new Intent(MainActivity.this, MainDashboard.class );
                startActivity(splashIntent);
                finish();
            }
        },SPLASH_TIME);
    }
}
