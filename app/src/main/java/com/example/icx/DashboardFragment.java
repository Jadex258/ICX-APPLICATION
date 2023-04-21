package com.example.icx;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import androidx.fragment.app.FragmentTransaction;


public class DashboardFragment extends Fragment {

    public DashboardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        Button signupButton = view.findViewById(R.id.myGetStarted);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, new RegisterFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // Find the View to animate
        View myView1 = view.findViewById(R.id.myView);
        View myView2 = view.findViewById(R.id.myView1);
        View myView3 = view.findViewById(R.id.myView2);

// Define the animation
        TranslateAnimation animation = new TranslateAnimation(0f, 300f, 0f, 0f);
        animation.setDuration(1000);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);

// Start the animation
        myView1.startAnimation(animation);
        myView2.startAnimation(animation);
        myView3.startAnimation(animation);


        return view;
    }
}
