package com.avijit.rms1.ui.relief_request_fragments;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.avijit.rms1.R;
import com.avijit.rms1.data.remote.model.ReliefRequest;
import com.avijit.rms1.data.remote.model.User;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.avijit.rms1.viewmodel.ReliefRequestForSelfViewModel;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestForSelfFragment extends Fragment {
    private static final String TAG = "RequestForSelfFragment";
    ReliefRequestForSelfViewModel viewModel;
    EditText familyMembersEditText,phoneNoEditText,addressEditText;
    TextView goButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_request_for_self,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ObjectAnimator.ofFloat(view,View.ALPHA,0f,1f).setDuration(500).start();
        viewModel = new ReliefRequestForSelfViewModel();
        initViews(view);
        String userString = getContext().getSharedPreferences("RMS",Context.MODE_PRIVATE).getString("user","");
        User user;
        try{
             user = new Gson().fromJson(userString,User.class);
        }
        catch (Exception e){
            Toast.makeText(getContext(), "Please log in again", Toast.LENGTH_SHORT).show();
            return;
        }

        goButton.setOnClickListener(v->{
            ReliefRequest reliefRequest = new ReliefRequest();
            reliefRequest.setUser_id(user.getId());
            reliefRequest.setRequest_time(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            reliefRequest.setRequest_for("self");
            reliefRequest.setFamily_members(familyMembersEditText.getText().toString());
            reliefRequest.setAddress(addressEditText.getText().toString());
            reliefRequest.setContact_no(phoneNoEditText.getText().toString());
            viewModel.saveReliefRequest(reliefRequest).observe(this, new Observer<NetworkResponse<ReliefRequest>>() {
                @Override
                public void onChanged(NetworkResponse<ReliefRequest> reliefRequestNetworkResponse) {
                    Log.d(TAG, "onChanged: "+reliefRequestNetworkResponse.toString());
                    if (reliefRequestNetworkResponse.isNetworkIsSuccessful()){
                        Toast.makeText(getContext(), "Successfully added", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "Failed to connect", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }
    private void initViews(View view){
        familyMembersEditText = view.findViewById(R.id.family_members_edit_text);
        phoneNoEditText = view.findViewById(R.id.phone_edit_text);
        addressEditText = view.findViewById(R.id.address_edit_text);
        goButton = view.findViewById(R.id.go);
    }
}
