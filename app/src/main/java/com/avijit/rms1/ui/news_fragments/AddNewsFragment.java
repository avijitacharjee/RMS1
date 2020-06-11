package com.avijit.rms1.ui.news_fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.avijit.rms1.R;
import com.avijit.rms1.data.remote.model.NewsSubtype;
import com.avijit.rms1.data.remote.model.NewsType;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.avijit.rms1.ui.BaseFragment;
import com.avijit.rms1.utils.AppUtils;
import com.avijit.rms1.viewmodel.AddNewsFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Avijit Acharjee on 6/10/2020 at 2:15 PM.
 * Email: avijitach@gmail.com.
 */
public class AddNewsFragment extends BaseFragment {
    private static final String TAG = "AddNewsFragment";
    private AddNewsFragmentViewModel viewModel;
    //Views
    private Spinner newsTypeSpinner, newsSubTypeSpinner;
    //Data
    private List<NewsType> typeList= new ArrayList<>();
    private List<NewsSubtype> subTypeList = new ArrayList<>();
    private List<String> typesStringList = new ArrayList<>();
    private List<String> subTypeStringList = new ArrayList<>();


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

        subTypeStringList.add("--Select subnews type--");
        newsSubTypeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,subTypeStringList);
        newsSubTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newsSubTypeSpinner.setAdapter(newsSubTypeAdapter);

    }
}
