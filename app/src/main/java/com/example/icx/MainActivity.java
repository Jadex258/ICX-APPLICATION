package com.example.icx;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;



import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.example.icx.databinding.ActivityMainBinding;
public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private MyDatabaseHelper dbHelper;
    SQLiteDatabase db;
    ActivityMainBinding binding;
    LoginFragment loginFragment;
    RegisterFragment registerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Create a new instance of DatabaseHelper
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);

// Get a writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

// Create a ContentValues object to store the values to be inserted
        ContentValues values = new ContentValues();
        values.put("username", "jade");
        values.put("password", "jademicha");
        values.put("last_login", "2023-04-20 12:00:00");
        values.put("is_superuser", false);
        values.put("last_name", "Smith");
        values.put("email", "jade@example.com");
        values.put("is_staff", false);
        values.put("is_active", true);
        values.put("date_joined", "2023-04-19 12:00:00");
        values.put("first_name", "Jade");


// Insert the new user into the database
        long newRowId = db.insert("auth_user", null, values);

// Check if the insertion was successful
        if (newRowId == -1) {
            Toast.makeText(this, "Error inserting user into database", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "User inserted into database with ID: " + newRowId, Toast.LENGTH_SHORT).show();
        }


        Cursor cursor = db.rawQuery("SELECT * FROM auth_user", null);

        loginFragment = new LoginFragment();
        registerFragment = new RegisterFragment();


        replaceFragment(new DashboardFragment());
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new DashboardFragment());
                    break;
                case R.id.services:
                    replaceFragment(new ServicesFragment());
                    break;
                case R.id.about:
                    replaceFragment(new AboutFragment());
                    break;
                case R.id.contact:
                    replaceFragment(new FeedbackFragment());
                    break;
            }
            return true;
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomDialog();
            }
        });

    }

    private void showPolicyDialog() {
        // Create dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_policy, null);
        builder.setView(view);

        // Get text view and set clickable span
        TextView policyLinksTextView = view.findViewById(R.id.DialogPolicyLinks);
        SpannableString ss = new SpannableString(policyLinksTextView.getText().toString());
        ClickableSpan privacyPolicySpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(MainActivity.this, PrivacyPolicyActivity.class);
                startActivity(intent);
            }
        };
        ClickableSpan termsOfUseSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(MainActivity.this, TermsConditionActivity.class);
                startActivity(intent);
            }
        };

        int privacyPolicyStart = ss.toString().indexOf("Privacy Policy");
        int privacyPolicyEnd = privacyPolicyStart + "Privacy Policy".length();
        int termsOfUseStart = ss.toString().indexOf("Terms of Use");
        int termsOfUseEnd = termsOfUseStart + "Terms of Use".length();
        ss.setSpan(new ForegroundColorSpan(Color.BLUE), privacyPolicyStart, privacyPolicyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(Color.BLUE), termsOfUseStart, termsOfUseEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new UnderlineSpan(), privacyPolicyStart, privacyPolicyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new UnderlineSpan(), termsOfUseStart, termsOfUseEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(privacyPolicySpan, privacyPolicyStart, privacyPolicyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(termsOfUseSpan, termsOfUseStart, termsOfUseEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        policyLinksTextView.setText(ss);
        policyLinksTextView.setMovementMethod(LinkMovementMethod.getInstance());

        // Set click listener for Agree button
        CheckBox checkBox = view.findViewById(R.id.checkbox_agree);
        Button btnAgree = view.findViewById(R.id.btn_agree);

        AlertDialog alertDialog = builder.create(); // Move declaration of alertDialog here
        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save that user has agreed to policy
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("is_first_launch", false);
                editor.apply();

                btnAgree.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.light_gray));
                btnAgree.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.dark_blue));

                // Dismiss dialog
                alertDialog.dismiss();
            }
        });

        // Disable Agree button by default
        btnAgree.setEnabled(false);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Enable or disable the agree button based on checkbox state
                btnAgree.setEnabled(isChecked);
            }
        });

        // Create and show dialog
        alertDialog.show();
    }



    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
        }

    public void hideNavigationBars() {
        binding.bottomAppBar.setVisibility(View.GONE);

    }

    public void showNavigationBars() {
        binding.bottomAppBar.setVisibility(View.VISIBLE);

    }

    private void showBottomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_layout);

        LinearLayout loginLayout = dialog.findViewById(R.id.login);
        LinearLayout signupLayout = dialog.findViewById(R.id.signup);
        LinearLayout liveLayout = dialog.findViewById(R.id.layoutLive);

        loginLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this,"Enter your credentials",Toast.LENGTH_SHORT).show();

                // Create an instance of your LoginFragment
                LoginFragment loginFragment = new LoginFragment();

                // Replace the current fragment with the LoginFragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, loginFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        signupLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Toast.makeText(MainActivity.this,"Create an account",Toast.LENGTH_SHORT).show();

                // Create an instance of your LoginFragment
                RegisterFragment registerFragment = new RegisterFragment();

                // Replace the current fragment with the RegisterFragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, registerFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });

        liveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Toast.makeText(MainActivity.this,"Go live is Clicked",Toast.LENGTH_SHORT).show();

            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        binding.bottomNavigationView.setVisibility(View.VISIBLE);
        binding.fab.setVisibility(View.VISIBLE);
    }
}
