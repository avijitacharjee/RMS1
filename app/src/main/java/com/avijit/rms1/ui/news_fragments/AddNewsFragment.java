package com.avijit.rms1.ui.news_fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.avijit.rms1.R;
import com.avijit.rms1.data.remote.model.News;
import com.avijit.rms1.data.remote.model.NewsSubtype;
import com.avijit.rms1.data.remote.model.NewsType;
import com.avijit.rms1.data.remote.model.User;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.avijit.rms1.ui.BaseFragment;
import com.avijit.rms1.utils.AppUtils;
import com.avijit.rms1.viewmodel.AddNewsFragmentViewModel;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Avijit Acharjee on 6/10/2020 at 2:15 PM.
 * Email: avijitach@gmail.com.
 */
public class AddNewsFragment extends BaseFragment {
    private static final String TAG = "AddNewsFragment";
    private static final int SELECT_NEWS_IMAGE = 1;
    private static final int SELECT_SUB_NEWS_IMAGE = 2;
    private AddNewsFragmentViewModel viewModel;
    //Views
    private Spinner newsTypeSpinner, newsSubTypeSpinner;
    private EditText newsTitleEditText,newsEditText,subNewsEditText;
    private TextView newsImageTextView,subnewsImageTextView,goTextView;
    private ImageView newsImageView,subNewsImageView;
    //Data
    private List<NewsType> typeList= new ArrayList<>();
    private List<NewsSubtype> subTypeList = new ArrayList<>();
    private List<String> typesStringList = new ArrayList<>();
    private List<String> subTypeStringList = new ArrayList<>();
    private Bitmap newsImageBitmap;
    private Bitmap subNewsImageBitmap;
    User user;

    ArrayAdapter<String> newsSubTypeAdapter, newsTypeAdapter;
    boolean isNewsTypeLoaded,isNewsSubTypeLoaded;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_news, null, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(AddNewsFragmentViewModel.class);
        appUtils = new AppUtils(getContext());
        Animation topAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.top_animation);
        view.setAnimation(topAnimation);
        initViews(view);
        newsTypeSpinner.setOnItemSelectedListener(dropdownItemSelectedListener);
        newsSubTypeSpinner.setOnItemSelectedListener(dropdownItemSelectedListener);
        newsImageTextView.setOnClickListener(this::newsImageOnClick);
        subnewsImageTextView.setOnClickListener(this::subNewsImageOnClick);
        goTextView.setOnClickListener(this::submit);
        appUtils.dialog.show();
        viewModel.getNewsTypes().observe(this,
                newsTypeResponse -> {
                    Log.d(TAG, "onViewCreated: " + newsTypeResponse.toString());
                    isNewsTypeLoaded=true;
                    if (isNewsSubTypeLoaded){
                        appUtils.dialog.dismiss();
                    }
                    if(newsTypeResponse.isNetworkIsSuccessful()){
                        typeList.clear();
                        typeList.addAll(newsTypeResponse.getData());
                        typesStringList.addAll(filterNewsType(newsTypeResponse.getData()));
                        newsTypeAdapter.notifyDataSetChanged();
                    }
                    else{
                        Toast.makeText(getContext(), "Failed to connect", Toast.LENGTH_SHORT).show();
                    }
                });
        viewModel.getNewsSubTypes().observe(this,
                newsSubTypeResponse -> {
                    Log.d(TAG, "onViewCreated: " + newsSubTypeResponse.toString());
                    isNewsSubTypeLoaded=true;
                    if (isNewsTypeLoaded){
                        appUtils.dialog.dismiss();
                    }
                    if(newsSubTypeResponse.isNetworkIsSuccessful() && newsSubTypeResponse.getData()!=null){
                        subTypeList.clear();
                        subTypeList.addAll(newsSubTypeResponse.getData());
                        subTypeStringList.addAll(filterNewsSubType(newsSubTypeResponse.getData()));
                        newsSubTypeAdapter.notifyDataSetChanged();
                    }
                    else{
                        Toast.makeText(getContext(), "Failed to connect", Toast.LENGTH_SHORT).show();
                    }

                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK && data!=null && data.getData()!=null){
            Uri filePath = data.getData();
            try{
                if(requestCode==SELECT_NEWS_IMAGE){
                    newsImageBitmap= MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),filePath);
                    newsImageView.setImageBitmap(newsImageBitmap);
                }
                else if(requestCode==SELECT_SUB_NEWS_IMAGE){
                    subNewsImageBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),filePath);
                    subNewsImageView.setImageBitmap(subNewsImageBitmap);
                }
            }catch (Exception e){}

        }
    }
    private String bitmapToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imageBytes=byteArrayOutputStream.toByteArray();
        String imageString = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        Log.d(TAG, imageString);
        return imageString;
    }
    private void submit(View view){
        if(!isFormValid()){
            appUtils.showValidationMessage(null);
            return;
        }
        appUtils.dialog.show();
        try {
            user = new Gson().fromJson(getContext().getSharedPreferences("RMS", Context.MODE_PRIVATE).getString("user",""),User.class);
        }
        catch (Exception e){
            user = new User();
            user.setId("1");
            user.setName("");
            user.setEmail("");
        }
        News news = new News();
        news.setDate(new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date()));
        news.setNews(newsEditText.getText().toString());
        news.setNews_title(newsTitleEditText.getText().toString());
        news.setSubnews(subNewsEditText.getText().toString());
        news.setStatus("1");
        news.setReporters_name(user.getName());
        news.setReporters_id(user.getId());
        news.setReporters_email(user.getEmail());
        news.setNews_subtypes_id(subTypeList.get(newsSubTypeSpinner.getSelectedItemPosition()-1).getId());
        news.setNews_types_id(typeList.get(newsTypeSpinner.getSelectedItemPosition()-1).getId());
        news.setNews_image(bitmapToString(newsImageBitmap));
        news.setSubnews_image(bitmapToString(subNewsImageBitmap));
        Log.d(TAG, "sendRequest: "+new Gson().toJson(news));

        viewModel.storeNews(news).observe(this,
                response->{
                    appUtils.dialog.dismiss();
                    Log.d(TAG, "onViewCreated: "+response.toString());
                    if(response.isNetworkIsSuccessful()){
                        Toast.makeText(getContext(), "News Added Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "Failed to connect", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void newsImageOnClick(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_NEWS_IMAGE);
    }
    private void subNewsImageOnClick(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_SUB_NEWS_IMAGE);
    }
    private List<String> filterNewsType(List<NewsType> newsTypeList){
        List<String> s = new ArrayList<>();
        for (int i =0;i<newsTypeList.size();i++){
            s.add(newsTypeList.get(i).getNews_types());
        }
        return s;
    }
    private List<String> filterNewsSubType(List<NewsSubtype> newsTypeList){
        List<String> s = new ArrayList<>();
        for (int i =0;i<newsTypeList.size();i++){
            s.add(newsTypeList.get(i).getNews_subtypes());
        }
        return s;
    }

    private void initViews(View view) {
        newsTypeSpinner = view.findViewById(R.id.news_type_spinner);
        newsSubTypeSpinner = view.findViewById(R.id.news_sub_type_spinner);

        typesStringList.add("--Select news type--");
        newsTypeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, typesStringList);
        newsTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newsTypeSpinner.setAdapter(newsTypeAdapter);

        subTypeStringList.add("--Select news subtype--");
        newsSubTypeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,subTypeStringList);
        newsSubTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newsSubTypeSpinner.setAdapter(newsSubTypeAdapter);

        newsTitleEditText = view.findViewById(R.id.news_title_edit_text);
        newsEditText = view.findViewById(R.id.news_edit_text);
        subNewsEditText= view.findViewById(R.id.sub_news_edit_text);

        newsImageTextView= view.findViewById(R.id.news_image_text_view);
        subnewsImageTextView = view.findViewById(R.id.sub_news_image_text_view);
        goTextView = view.findViewById(R.id.go);

        newsImageView = view.findViewById(R.id.news_image_view);
        subNewsImageView = view.findViewById(R.id.sub_news_image_view);

    }
    private boolean isFormValid(){
        boolean valid = true;
        if(newsTypeSpinner.getSelectedItemPosition()==0 || newsSubTypeSpinner.getSelectedItemPosition()==0){
            valid = false;
        }
        if(newsEditText.equals("") || subNewsEditText.equals("") || newsTitleEditText.equals(""))
        {
            valid = false;
        }
        if(newsImageBitmap==null || subNewsImageBitmap == null){
            valid=false;
        }
        return valid;
    }
}
