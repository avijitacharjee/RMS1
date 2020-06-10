package com.avijit.rms1.ui.news_fragments;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.avijit.rms1.R;
import com.avijit.rms1.data.remote.model.NewsSubtype;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.avijit.rms1.ui.BaseFragment;
import com.avijit.rms1.utils.AppUtils;
import com.avijit.rms1.viewmodel.AddNewsSubTypeFragmentViewModel;

/**
 * Created by Avijit Acharjee on 6/10/2020 at 12:27 PM.
 * Email: avijitach@gmail.com.
 */
public class AddNewsSubTypeFragment extends BaseFragment {
    private static final String TAG = "AddNewsSubTypeFragment";
    AddNewsSubTypeFragmentViewModel viewModel;
    EditText editText;
    TextView nextButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_news_sub_type,null,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        appUtils = new AppUtils(getContext());
        Animation topAnimation = AnimationUtils.loadAnimation(getContext(),R.anim.top_animation);
        view.startAnimation(topAnimation);
        initViews(view);
        viewModel = ViewModelProviders.of(this).get(AddNewsSubTypeFragmentViewModel.class);
        nextButton.setOnClickListener(this::submit);
    }
    private void submit(View view){
        if(isFormValid()){
            NewsSubtype newsSubtype = new NewsSubtype();
            newsSubtype.setNews_subtypes(editText.getText().toString());
            appUtils.dialog.show();
            viewModel.storeNewsSubType(newsSubtype).observe(this,newsSubTypeObserver);
        }
        else {
            appUtils.showValidationMessage(null);
        }
    }
    private Observer<NetworkResponse<NewsSubtype>> newsSubTypeObserver = newsSubtypeNetworkResponse -> {
        appUtils.dialog.dismiss();
        Log.d(TAG, newsSubtypeNetworkResponse.toString());
        if(newsSubtypeNetworkResponse.isNetworkIsSuccessful()){
            Toast.makeText(getContext(), "Subtype added successfully", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Failed to connect", Toast.LENGTH_SHORT).show();
        }
    };
    private boolean isFormValid(){
        boolean valid = true;
        if(editText.getText().toString().equals("")){
            valid=false;
        }
        return valid;
    }
    private void initViews(View view){
        editText= view.findViewById(R.id.news_type_edit_text);
        nextButton = view.findViewById(R.id.next_button);
    }
}
