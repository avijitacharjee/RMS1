package com.avijit.rms1.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.avijit.rms1.R;
import com.avijit.rms1.data.remote.model.Relief;
import com.avijit.rms1.data.remote.responses.ReliefStoreResponse;
import com.avijit.rms1.location.AppLocationService;
import com.avijit.rms1.viewmodel.StoreReliefViewModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StoreRelief extends BaseActivity {
    StoreReliefViewModel viewModel;
    TextView chooseImageButton,addButton,addAndNextButton,addressTextView;
    EditText fullName,nid, contactNo, members, earningMembers;
    ImageView imageView;
    private Bitmap bitmap;
    int PICK_IMAGE_REQUEST=111;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    ProgressDialog progressDialog;
    private double latitude =0;
    private double longitude=0;

    private static final String TAG = "StoreRelief";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_relief);

         /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("RMS"); */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chooseImageButton = findViewById(R.id.img);
        imageView=findViewById(R.id.imageinsert);
        fullName = findViewById(R.id.full_name_edit_text);
        nid = findViewById(R.id.nid_edit_text);
        contactNo = findViewById(R.id.contact_no_edit_text);
        members = findViewById(R.id.members_edit_text);
        earningMembers = findViewById(R.id.earning_members_edit_text);
        addButton = findViewById(R.id.add_button);
        addAndNextButton = findViewById(R.id.add_and_next_button);
        addressTextView = findViewById(R.id.address_text_view);
        addressTextView.setText("Address: "+getIntent().getExtras().getString("district")+","+getIntent().getExtras().getString("area")+","+getIntent().getExtras().getString("address"));
        viewModel = ViewModelProviders.of(this).get(StoreReliefViewModel.class);
        try{
            latitude = new AppLocationService(StoreRelief.this).getLocation().getLatitude();
            longitude = new AppLocationService(StoreRelief.this).getLocation().getLongitude();
        }catch (Exception e){
            latitude = 0.0;
            longitude= 0.0;
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //final String name = fullName.getText().toString();
                if(isFormValid()){
                    /**
                     * Relief Object
                     *
                     */
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                    final Relief relief = new Relief();
                    relief.setDivision_id(getIntent().getExtras().getString("divisionId"));
                    relief.setDistrict_id(getIntent().getExtras().getString("districtId"));
                    relief.setArea_id(getIntent().getExtras().getString("areaId"));
                    relief.setAddress(getIntent().getExtras().getString("address"));
                    relief.setNid(nid.getText().toString());
                    relief.setMembers_in_family(members.getText().toString());
                    relief.setEarning_members(earningMembers.getText().toString());
                    relief.setLat(latitude+"");
                    relief.setLongitude(longitude+"");
                    relief.setImage(imageString);
                    relief.setContact_no(contactNo.getText().toString());
                    relief.setDate_given(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    relief.setGiven_by("1");
                    relief.setGiven_to("1");
                    viewModel.storeRelief(relief).observe(StoreRelief.this, new Observer<ReliefStoreResponse>() {
                        @Override
                        public void onChanged(ReliefStoreResponse reliefStoreResponse) {
                            Log.d(TAG, "onChanged: " + reliefStoreResponse.toString());
                        }
                    });
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(),"All fields are required" , Toast.LENGTH_SHORT);
                    View view = toast.getView();

                    //Gets the actual oval background of the Toast then sets the colour filter
                    view.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

                    //Gets the TextView from the Toast so it can be editted
                    TextView text = view.findViewById(android.R.id.message);
                    text.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                    text.setTextColor(Color.WHITE);

                    toast.show();
                }



                /*if (isFormValid())
                {
                    Toast.makeText(FamilyRegistration.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        addAndNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        //final String name = fullName.getText().toString();
                        if(isFormValid()){
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] imageBytes = baos.toByteArray();
                            final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                            final Relief relief = new Relief();
                            relief.setDivision_id(getIntent().getExtras().getString("divisionId"));
                            relief.setDistrict_id(getIntent().getExtras().getString("districtId"));
                            relief.setArea_id(getIntent().getExtras().getString("areaId"));
                            relief.setAddress(getIntent().getExtras().getString("address"));
                            relief.setNid(nid.getText().toString());
                            relief.setMembers_in_family(members.getText().toString());
                            relief.setEarning_members(earningMembers.getText().toString());
                            relief.setLat(latitude+"");
                            relief.setLongitude(longitude+"");
                            relief.setImage(imageString);
                            relief.setContact_no(contactNo.getText().toString());
                            relief.setDate_given(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                            relief.setGiven_by("1");
                            relief.setGiven_to("1");
                            viewModel.storeRelief(relief).observe(StoreRelief.this, new Observer<ReliefStoreResponse>() {
                                @Override
                                public void onChanged(ReliefStoreResponse reliefStoreResponse) {
                                    Log.d(TAG, "onChanged: " + reliefStoreResponse.toString());
                                }
                            });
                        }
                        else {
                            Toast toast = Toast.makeText(getApplicationContext(),"All fields are required" , Toast.LENGTH_SHORT);
                            View view = toast.getView();

                            //Gets the actual oval background of the Toast then sets the colour filter
                            view.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

                            //Gets the TextView from the Toast so it can be editted
                            TextView text = view.findViewById(android.R.id.message);
                            text.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                            text.setTextColor(Color.WHITE);

                            toast.show();
                        }



                /*if (isFormValid())
                {
                    Toast.makeText(FamilyRegistration.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                }*/
                    }

        });


        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage(v);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        super.onBackPressed();
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();

            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                //Setting image to ImageView
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }
    private String birthimagetostring(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imagebyte=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imagebyte, Base64.DEFAULT);
    }
    public void chooseImage(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }
    private void onCaptureImageResult(Intent data) {
        bitmap = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageView.setImageBitmap(bitmap);
    }
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        imageView.setImageBitmap(bm);
    }
    private boolean isFormValid() {
        boolean valid = true;
        if(fullName.getText().toString().isEmpty())
        {
            valid = false;
        }
        if(nid.getText().toString().isEmpty())
        {
            valid=false;
        }
        if(contactNo.getText().toString().length()==0)
        {
            valid= false;
        }
        if(members.getText().toString().isEmpty()){
            valid = false;
        }
        if(earningMembers.getText().toString().isEmpty())
        {
            valid = false;
        }
        if(bitmap==null){
            valid = false;
        }
        return valid;
    }
}
