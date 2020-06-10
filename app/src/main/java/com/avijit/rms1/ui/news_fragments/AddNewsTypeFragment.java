package com.avijit.rms1.ui.news_fragments;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.avijit.rms1.R;
import com.avijit.rms1.data.remote.model.NewsType;
import com.avijit.rms1.ui.BaseFragment;
import com.avijit.rms1.utils.AppUtils;
import com.avijit.rms1.viewmodel.AddNewsTypeFragmentViewModel;

/**
 * Created by Avijit Acharjee on 6/8/2020 at 12:19 PM.
 * Email: avijitach@gmail.com.
 */
public class AddNewsTypeFragment extends BaseFragment {
    private static final String TAG = "AddNewsTypeFragment";
    private AddNewsTypeFragmentViewModel viewModel;
    private EditText newsTypeEditText;
    private TextView nextButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_news_type, null, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //ObjectAnimator.ofFloat(view,View.ALPHA,0f,1f).setDuration(500).start();
        animations(view);
        viewModel = ViewModelProviders.of(this).get(AddNewsTypeFragmentViewModel.class);
        initViews(view);
        appUtils = new AppUtils(getContext());
        nextButton.setOnClickListener(this::submit);
    }
    private void animations(View view){
        /*topAnimation = AnimationUtils.loadAnimation(getContext(),R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(getContext(),R.anim.bottom_animation);
        view.startAnimation(topAnimation);*/
        ObjectAnimator.ofFloat(view,View.TRANSLATION_Y,0,100).setDuration(1000).start();
    }
    private void submit(View view){
        NewsType newsType= new NewsType();
        newsType.setNews_types(newsTypeEditText.getText().toString());
        appUtils.dialog.show();
        viewModel.storeNewsType(newsType).observe(this,
                response->{
                    Log.d(TAG, "onViewCreated: "+response.toString());
                    appUtils.dialog.dismiss();
                    if(response.isNetworkIsSuccessful()){
                        Toast.makeText(getContext(), "Successfully added", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "Failed to connect", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void initViews(View view){
        newsTypeEditText=view.findViewById(R.id.news_type_edit_text);
        nextButton = view.findViewById(R.id.next_button);
    }
}
