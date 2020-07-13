package com.avijit.rms1.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.avijit.rms1.R;
import com.avijit.rms1.data.remote.responses.CoronaSummaryResponse;
import com.avijit.rms1.databinding.ActivityMainDashboardBinding;
import com.avijit.rms1.utils.AppUtils;
import com.avijit.rms1.viewmodel.MainDashBoardViewModel;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;

public class MainDashboard extends BaseActivity {
    private static final String TAG = "MainDashboard";
    private static final int PERMISSION_REQUEST_CODE = 200;
    TextView textView;
    MainDashBoardViewModel mainDashBoardViewModel;
    AlertDialog dialog;
    View view;
    Animation topAnimation;
    CardView card1;
    LinearLayout nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);
        //textView = findViewById(R.id.msg);
        appUtils = new AppUtils(this);
        initView();

        view = findViewById(R.id.bg_top_header);
        card1 = findViewById(R.id.card_1);
        setToolbar();
        toolbar.setTitle("RMS");
        toolbar.setSubtitle("Covid-19 Disease update");
        setSupportActionBar(toolbar);
        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        card1.startAnimation(topAnimation);

        appUtils = new AppUtils(this);
        mainDashBoardViewModel = ViewModelProviders.of(this).get(MainDashBoardViewModel.class);
        mainDashBoardViewModel.init();
        appUtils.dialog.show();
        mainDashBoardViewModel.getStudentRepository().observe(this, coronaSummaryResponse -> {
           appUtils.dialog.dismiss();
           if(!coronaSummaryResponse.isNetworkIsSuccessful()){
               Toast.makeText(MainDashboard.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
           }
           requestPermission();
        });
        ActivityMainDashboardBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main_dashboard);
        binding.setLifecycleOwner(this);
        binding.setViewmodel(mainDashBoardViewModel);
        //nextButton.setOnClickListener(this::nextButtonClick);

    }

    @Override
    public void onBackPressed() {
        exitDialog();
    }
    private void exitDialog(){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainDashboard.this);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
                //System.exit(0);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setMessage("Are you sure to exit?");

        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION, CAMERA}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean fineLocationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean coraseLocationAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    if (fineLocationAccepted && coraseLocationAccepted && cameraAccepted)
                    {

                    }
                    //Snackbar.make(view, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                    else {

                        // Snackbar.make(view, "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)
                                    || shouldShowRequestPermissionRationale(ACCESS_COARSE_LOCATION)
                                    || shouldShowRequestPermissionRationale(CAMERA)
                            ) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                requestPermission();
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }
                break;
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new androidx.appcompat.app.AlertDialog.Builder(MainDashboard.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                })
                .create()
                .show();
    }
    public void initView(){
        nextButton = findViewById(R.id.next_button);
    }
    public void nextButtonClick(View view){
        Log.d(TAG, "nextButtonClick: ");
        appUtils.dialog.show();
        mainDashBoardViewModel.cacheLocations().observe(this,successful->{
            appUtils.dialog.dismiss();
            if(successful){
                startActivity(new Intent(MainDashboard.this,Nav.class));
            }
            else {
                Toast.makeText(this, "Failed to connect. Try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
