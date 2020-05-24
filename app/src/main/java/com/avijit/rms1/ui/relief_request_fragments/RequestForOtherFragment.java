package com.avijit.rms1.ui.relief_request_fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.avijit.rms1.R;
import com.avijit.rms1.adapters.RequestForOtherRecyclerViewAdapter;

public class RequestForOtherFragment extends Fragment {
    private static final String TAG = "RequestForOtherFragment";
    RecyclerView recyclerView;
    TextView textView;
    RequestForOtherRecyclerViewAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_request_for_other,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RequestForOtherRecyclerViewAdapter(5);
        recyclerView.setAdapter(adapter);
        textView = view.findViewById(R.id.textView);
        textView.setOnClickListener(v -> {
            adapter.notifyDataSetChanged();
            Log.d(TAG, "onViewCreated: "+adapter.getData());
        });
    }
}
