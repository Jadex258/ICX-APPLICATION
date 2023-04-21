package com.example.icx;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginFragment extends Fragment {
    private EditText usernameEditText;
    private MainActivity mainActivity;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView registerTextView;
    private SQLiteDatabase db;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        mainActivity.hideNavigationBars();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mainActivity.binding.bottomAppBar.setVisibility(View.GONE);
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.GONE);


        usernameEditText = view.findViewById(R.id.emailtext);
        passwordEditText = view.findViewById(R.id.userpass);
        loginButton = view.findViewById(R.id.LoginButton);

        // Create a new instance of MyDatabaseHelper
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(getActivity());

        // Get a readable database
        db = dbHelper.getReadableDatabase();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(getActivity(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if the user exists in the database
                    Cursor cursor = db.rawQuery("SELECT * FROM auth_user WHERE username=? AND password=?", new String[]{username, password});

                    if (cursor.moveToFirst()) {
                        Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getActivity(), UserMainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        // User does not exist, login failed
                        Toast.makeText(getActivity(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }

                    // Close the cursor to avoid memory leaks
                    cursor.close();
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroyView();
        mainActivity.showNavigationBars();
        super.onDestroy();
        // Close the database connection to avoid memory leaks
        db.close();
    }
}

