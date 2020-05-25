package com.avijit.rms1.ui.relief_request_fragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
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
    LinearLayout element;

    float dX;
    float dY;
    int lastAction;

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

        element = view.findViewById(R.id.element);
        /*element.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = view.getX() - event.getRawX();
                        dY = view.getY() - event.getRawY();
                        lastAction = MotionEvent.ACTION_DOWN;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        view.setY(event.getRawY() + dY);
                        view.setX(event.getRawX() + dX);
                        lastAction = MotionEvent.ACTION_MOVE;
                        break;

                    case MotionEvent.ACTION_UP:
                        if (lastAction == MotionEvent.ACTION_DOWN)
                            Toast.makeText(getContext(), "Clicked!", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        return false;
                }
                return true;
            }
        });*/

        selfTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestForSelfFragment selfFragment = new RequestForSelfFragment();
                FragmentManager fragmentManager= getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                /*ft.setCustomAnimations(R.anim.enter_ritht_to_left,R.anim.exit_right_to_left,
                        R.anim.enter_left_to_right,R.anim.exit_left_to_right);*/
                ft.replace(R.id.relief_request_fragment_container,selfFragment);
               // ft.addSharedElement(element,"element");
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
