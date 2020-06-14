package com.avijit.rms1.ui.news_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.avijit.rms1.R;
import com.avijit.rms1.adapters.NewsRecyclerViewAdapter;
import com.avijit.rms1.data.remote.model.News;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.avijit.rms1.ui.BaseFragment;
import com.avijit.rms1.viewmodel.NewsDynamicFragmentViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Avijit Acharjee on 6/12/2020 at 6:56 PM.
 * Email: avijitach@gmail.com.
 */
public class NewsDynamicFragment extends BaseFragment {
    private static final String TAG = "NewsDynamicFragment";
    NewsDynamicFragmentViewModel viewModel;
    View view;
    TextView c;
    private RecyclerView recyclerView;
    //Data
    private List<News> newsList;
    NewsRecyclerViewAdapter adapter;
    public View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(),NewsDetails.class);
            intent.putExtra("news",new Gson().toJson(newsList.get(recyclerView.indexOfChild(v))));
            startActivity(intent);
        }
    };
    public static NewsDynamicFragment newInstance(String val, String newsListJson) {
        NewsDynamicFragment fragment = new NewsDynamicFragment();
        Bundle args = new Bundle();
        args.putString("someValue", val);
        args.putString("news",newsListJson);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news_dynamic, container, false);
        String val = getArguments().getString("someValue", "0");
        c = view.findViewById(R.id.textView);
        c.setText(val);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(NewsDynamicFragmentViewModel.class);
        initViews(view);
        newsList = new ArrayList<>();
        //viewModel.getAllNews().observe(this, this::setNews);
        adapter = new NewsRecyclerViewAdapter(newsList, onItemClickListener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        setNews();
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
    }

    private void setNews() {
        Log.d(TAG, "setNews: " + new Gson().toJson(newsList));
        String newsListJson = getArguments().getString("news");
        List<News> list= new ArrayList<>();
        try {
            Type type = new TypeToken<List<News>>() {}.getType();
            list= new Gson().fromJson(newsListJson,type);
        }catch (Exception e) {
        }
        String type = getArguments().getString("someValue");
        this.newsList.clear();
        for(int i =0;i<list.size();i++){
            if (list.get(i).getNews_types().equals(type)){
                this.newsList.add(list.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }



}
