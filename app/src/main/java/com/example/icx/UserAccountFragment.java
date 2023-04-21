package com.example.icx;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class UserAccountFragment extends Fragment {

    private TextView accountInfoTextView;
    private TextView forgotPasswordTextView;
    private TextView privacyPolicyTextView;
    private TextView termsConditionsTextView;
    private TextView contactUsTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_account, container, false);

        // Find the TextViews by ID and assign them to variables
        accountInfoTextView = view.findViewById(R.id.account_info_textview);
        forgotPasswordTextView = view.findViewById(R.id.forgot_password_textview);
        privacyPolicyTextView= view.findViewById(R.id.privacy_policy_textview);
        termsConditionsTextView= view.findViewById(R.id.terms_conditions_textview);
        contactUsTextView = view.findViewById(R.id.contact_us_textview);

        // Set an OnClickListener on the accountInfoTextView variable to launch the AccountInfoActivity
        accountInfoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent accountInfoIntent = new Intent(getActivity(), AccountInfoActivity.class);
                startActivity(accountInfoIntent);
            }
        });

        // Set an OnClickListener on the forgotPasswordTextView variable to launch the ForgotPasswordActivity
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotPasswordIntent = new Intent(getActivity(), ForgotPasswordActivity.class);
                startActivity(forgotPasswordIntent);
            }
        });

        // Set an OnClickListener on the privacyPolicyTextView variable to launch the PrivacyPolicyActivity
        privacyPolicyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent privacyPolicyIntent = new Intent(getActivity(), PrivacyPolicyActivity.class);
                startActivity(privacyPolicyIntent);
            }
        });

        // Set an OnClickListener on the privacyPolicyTextView variable to launch the PrivacyPolicyActivity
        termsConditionsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent termsConditionsIntent = new Intent(getActivity(), TermsConditionActivity.class);
                startActivity(termsConditionsIntent);
            }
        });

        // Set an OnClickListener on the contactUsTextView variable to launch the FeedbackFragment
        contactUsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedbackFragment feedbackFragment = new FeedbackFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_user_layout, feedbackFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        return view;
    }

}









