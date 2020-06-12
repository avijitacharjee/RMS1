package com.avijit.rms1.viewmodel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avijit.rms1.data.remote.model.News;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.avijit.rms1.repository.NewsRepository;

import java.util.List;

/**
 * Created by Avijit Acharjee on 6/12/2020 at 11:54 PM.
 * Email: avijitach@gmail.com.
 */
public class NewsDynamicFragmentViewModel extends ViewModel {
    private NewsRepository newsRepository;
    private View.OnClickListener onClickListener;
    public NewsDynamicFragmentViewModel(){
        newsRepository=NewsRepository.getInstance();
    }
    public MutableLiveData<NetworkResponse<List<News>>> getAllNews(){
        return newsRepository.getAllNews();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }
}
