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
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.avijit.rms1.R;
import com.avijit.rms1.data.remote.model.News;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.avijit.rms1.viewmodel.NewsHomeFragmentViewModel;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Avijit Acharjee on 6/7/2020 at 7:40 PM.
 * Email: avijitach@gmail.com.
 */
public class NewsHomeFragment extends Fragment {
    NewsHomeFragmentViewModel viewModel;
    private static final String TAG = "NewsHomeFragment";
    ImageView imageView;
    //Views
    TextView selectImageTextView;
    private Bitmap bitmap;
    private static int PICK_IMAGE_REQUEST = 111;
    private static int GET_IMAGE_FROM_GALLERY = 111;
    private static int REQUEST_CAMERA=0,SELECT_FILE=1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_home,null,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(view);
        viewModel= ViewModelProviders.of(this).get(NewsHomeFragmentViewModel.class);

        selectImageTextView.setOnClickListener(
                this::chooseImage
        );

    }
    private void chooseImage(View view){
        /*Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent,SELECT_FILE);*/
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_FILE);

    }
    private void initViews(View view){
        selectImageTextView = view.findViewById(R.id.gallery_text_view);
        imageView = view.findViewById(R.id.image_view);
    }
    private String bitmapToString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imageBytes=byteArrayOutputStream.toByteArray();
        String imageString = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        Log.d(TAG, imageString);
        return imageString;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SELECT_FILE && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),filePath);
                imageView.setImageBitmap(bitmap);
                Log.d(TAG, bitmapToString());
                sendRequest();
            }catch (Exception e ){
                e.printStackTrace();
            }
        }
        if(resultCode == RESULT_OK){
            if(requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);
            }
            else if( requestCode == REQUEST_CAMERA){
                onCaptureImageResult(data);
            }
        }
    }
    private void sendRequest(){
        News news = new News();
        news.setDate("2020-02-01");
        news.setNews("abcd");
        news.setNews_title("title");
        news.setSubnews("lkajsd");
        news.setStatus("1");
        news.setReporters_name("abc");
        news.setReporters_id("1");
        news.setReporters_email("abc@gmail.com");
        news.setNews_subtypes_id("1");
        news.setNews_types_id("1");
        news.setNews_image(bitmapToString());
        Log.d(TAG, "sendRequest: "+new Gson().toJson(news));
        viewModel.storeNews(news).observe(this,
                response->{
                    Log.d(TAG, "onViewCreated: "+response.toString());
                });
    }
    private void onCaptureImageResult(Intent data){
        bitmap = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        File destination = new File(Environment.getExternalStorageDirectory(),System.currentTimeMillis()+".jpg");
        FileOutputStream fileOutputStream;
        try {
            destination.createNewFile();
            fileOutputStream = new FileOutputStream(destination);
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
            fileOutputStream.close();
        } catch (Exception e){

        }
        //this.bitmap = bitmap;
    }
    private void onSelectFromGalleryResult(Intent data){
        Bitmap bitmap = null;
        if (data!=null){
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),data.getData());
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        this.bitmap = bitmap;
    }
}
