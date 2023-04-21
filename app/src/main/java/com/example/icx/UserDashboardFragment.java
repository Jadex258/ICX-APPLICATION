package com.example.icx;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
public class UserDashboardFragment extends Fragment {

    private TextView greetingText;
    private MyDatabaseHelper dbHelper;
    private String username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_dashboard, container, false);

        greetingText = rootView.findViewById(R.id.greeting_text);
        dbHelper = new MyDatabaseHelper(getContext());

        // Retrieve the username from the shared preferences
        SharedPreferences sharedPref = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        username = sharedPref.getString("username", "");

        // Retrieve the user's first name from the database and set the greeting text
        String firstName = dbHelper.getUserFirstName(username);
        if (firstName != null) {
            greetingText.setText("Hello " + firstName + "!");
        }

        // Set up the view pager and tab layout
        ViewPager2 viewPager = rootView.findViewById(R.id.view_pager);
        TabLayout tabLayout = rootView.findViewById(R.id.tab_layout);
        viewPager.setAdapter(new ViewPagerAdapter(this));
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(position == 0 ? "Dashboard" : "Post")
        ).attach();

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

    private static class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return position == 0 ? new UserHomeFragment() : new UserServicesFragment();
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}
