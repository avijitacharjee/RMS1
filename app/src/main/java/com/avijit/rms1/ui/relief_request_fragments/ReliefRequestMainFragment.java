package com.avijit.rms1.ui.relief_request_fragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.avijit.rms1.R;

public class ReliefRequestMainFragment extends Fragment {
    Animation topAnimation,bottomAnimation;
    TextView selfTextView,otherTextView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view= inflater.inflate(R.layout.fragment_add_request_main,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        selfTextView= view.findViewById(R.id.self_button);
        otherTextView = view.findViewById(R.id.other_button);

        topAnimation = AnimationUtils.loadAnimation(getContext(),R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(getContext(),R.anim.bottom_animation);
        view.startAnimation(topAnimation);
        //otherTextView.startAnimation(bottomAnimation);

        AnimatorSet animationSet = new AnimatorSet();
        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationY", 100f);
        animation.setDuration(2000);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(ObjectAnimator.REVERSE);
        animation.start();

        /*ObjectAnimator animation1 = ObjectAnimator.ofFloat(view,"translationY",-100f);
        animation1.setDuration(2000);
        animation1.setRepeatCount(Animation.INFINITE);
        animationSet.play(animation1).after(animation);
        animationSet.start();*/
        selfTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestForSelfFragment selfFragment = new RequestForSelfFragment();
                FragmentManager fragmentManager= getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.relief_request_fragment_container,selfFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        otherTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestForOtherFragment otherFragment = new RequestForOtherFragment();
                FragmentManager fragmentManager= getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.relief_request_fragment_container,otherFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }
}
