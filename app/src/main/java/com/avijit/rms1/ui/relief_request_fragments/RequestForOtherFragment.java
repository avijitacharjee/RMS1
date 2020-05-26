package com.avijit.rms1.ui.relief_request_fragments;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.avijit.rms1.R;
import com.avijit.rms1.data.remote.model.ReliefRequest;
import com.avijit.rms1.data.remote.model.User;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.avijit.rms1.viewmodel.ReliefRequestForOtherViewModel;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RequestForOtherFragment extends Fragment {
    private static final String TAG = "RequestForOtherFragment";
    private ReliefRequestForOtherViewModel viewModel;
    private TextView numberPickerButton;
    private LinearLayout elements;
    private NumberPicker numberPicker;
    private List<View> viewList;
    private LinearLayout linearLayout;
    private TextView goButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_request_for_other, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ObjectAnimator.ofFloat(view,View.ALPHA,0f,1f).setDuration(500).start();

        viewModel= ViewModelProviders.of(this).get(ReliefRequestForOtherViewModel.class);
        elements = view.findViewById(R.id.element);
        numberPickerButton = view.findViewById(R.id.number_picker_dialog_text_view);
        /*TextInputLayout phoneTextInputLayout = new TextInputLayout(new ContextThemeWrapper(getContext(),R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox));
        phoneTextInputLayout.setHint("Phone");
        phoneTextInputLayout.setBoxStrokeColor(Color.BLACK);
        phoneTextInputLayout.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black)));
        phoneTextInputLayout.setHintAnimationEnabled(true);
        phoneTextInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black)));

        TextInputLayout.LayoutParams phoneTextInputLayoutLayoutParams = new TextInputLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        phoneTextInputLayout.setLayoutParams(phoneTextInputLayoutLayoutParams);
        phoneTextInputLayout.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);

        TextInputEditText phoneTextInputEditText = new TextInputEditText(getContext());
        phoneTextInputEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        phoneTextInputEditText.setTextColor(getResources().getColor(R.color.black_1));
        phoneTextInputEditText.setLayoutParams(phoneTextInputLayoutLayoutParams);


        TextInputLayout passTextInputLayout = new TextInputLayout(getContext(), null, R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox);

        passTextInputLayout.setHint("Please Enter Password");
        passTextInputLayout.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);
        TextInputEditText edtPass = new TextInputEditText(passTextInputLayout.getContext());
        passTextInputLayout.addView(edtPass);



        phoneTextInputLayout.addView(phoneTextInputEditText);
        elements.addView(passTextInputLayout);
        elements.addView(phoneTextInputLayout);*/
        /* View view1 = getLayoutInflater().inflate(R.layout.item_request_for_other,null);
        View view2 = getLayoutInflater().inflate(R.layout.item_request_for_other,null);
        elements.addView(view1);
        elements.addView(view2);*/

        /**
         *
         * For the go button
         */
        linearLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams lParamMR=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lParamMR.setMargins(0,30,0,30);
        linearLayout.setLayoutParams(lParamMR);
        linearLayout.setBackground(getResources().getDrawable(R.drawable.shape1));
        linearLayout.setElevation(10f);
        goButton = new TextView(getContext());
        goButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        goButton.setBackgroundColor(getResources().getColor(R.color.black_1));
        goButton.setGravity(Gravity.CENTER);
        goButton.setText("GO");
        goButton.setPadding(10,10,10,10);
        goButton.setTextColor(getResources().getColor(R.color.white_1));
        goButton.setOnClickListener(v->{
            Log.d(TAG, "onViewCreated: "+viewList);
            String familyMembersString ="";
            String phoneNoString="";
            String addressString="";
            String nidString="";
            for(int i=0;i<viewList.size();i++){
                View form = viewList.get(i);
                familyMembersString += ((EditText)form.findViewById(R.id.family_members_edit_text)).getText().toString();
                phoneNoString +=((EditText)form.findViewById(R.id.phone_edit_text)).getText().toString();
                addressString+=((EditText)form.findViewById(R.id.address_edit_text)).getText().toString();
                nidString+=((EditText)form.findViewById(R.id.nid_edit_text)).getText().toString();
                if (i<viewList.size()-1){
                    familyMembersString+=" ";
                    phoneNoString+=" ";
                    addressString+="!@";
                    nidString+=" ";
                }
            }
            String userString = getContext().getSharedPreferences("RMS", Context.MODE_PRIVATE).getString("user","");
            User user;
            try{
                user = new Gson().fromJson(userString,User.class);
            }
            catch (Exception e){
                Toast.makeText(getContext(), "Please log in again", Toast.LENGTH_SHORT).show();
                return;
            }
            ReliefRequest reliefRequest = new ReliefRequest();
            reliefRequest.setUser_id(user.getId());
            reliefRequest.setFamily_members(familyMembersString);
            reliefRequest.setPhone(phoneNoString);
            reliefRequest.setAddress(addressString);
            reliefRequest.setRequest_time(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            reliefRequest.setRequest_for("others");
            reliefRequest.setFamily_no(numberPicker.getValue()+"");
            reliefRequest.setContact_no(phoneNoString);
            reliefRequest.setNid(nidString);
            viewModel.saveReliefRequest(reliefRequest).observe(this, new Observer<NetworkResponse<ReliefRequest>>() {
                @Override
                public void onChanged(NetworkResponse<ReliefRequest> reliefRequestNetworkResponse) {
                    Log.d(TAG, "onChanged: "+new Gson().toJson(reliefRequest));
                    Log.d(TAG, "onChanged: "+reliefRequestNetworkResponse.toString());
                    if(reliefRequestNetworkResponse.isNetworkIsSuccessful()){
                        Toast.makeText(getContext(), "Inserted Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "Failed to connect", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
        /**
         * Number picker listener
         */
        numberPickerButton.setOnClickListener(v -> {
            numberPicker = new NumberPicker(getContext());
            numberPicker.setMinValue(1);
            numberPicker.setMaxValue(50);

            new AlertDialog.Builder(getContext())
                    .setView(numberPicker)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            numberPickerButton.setText("Number of families: " + numberPicker.getValue() + "");
                            viewList = new ArrayList<>();
                            elements.removeAllViews();
                            for (int i = 0; i < numberPicker.getValue(); i++) {
                                View view1 = getLayoutInflater().inflate(R.layout.item_request_for_other, null);
                                TextView textView = view1.findViewById(R.id.family_no_text_view);
                                textView.setText("Family no: " + (i + 1));
                                viewList.add(view1);
                                elements.addView(view1);
                            }
                            linearLayout.removeAllViews();
                            linearLayout.addView(goButton);
                            elements.addView(linearLayout);
                        }
                    })
                    .show();
        });


    }
}
