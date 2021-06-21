package com.finalproject.ncovitrackerproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.finalproject.ncovitrackerproject.Models.AppState;
import com.finalproject.ncovitrackerproject.Shared_Preferences.DataLocalManager;
import com.finalproject.ncovitrackerproject.Shared_Preferences.MyApplication;
import com.finalproject.ncovitrackerproject.Shared_Preferences.SharedPreference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    private LinearLayout update_info_lin, change_password_ln,logout;
    private TextView textViewName, textViewEmail;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference DataTT;
    FirebaseUser user;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Tài khoản của tôi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Init();
        getName();
        update_info_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, UpdateInfoActivity.class));
            }
        });
        change_password_ln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ChangePasswordActivity.class));
                finish();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
finish();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void getName() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        DataTT = firebaseDatabase.getReference("Thông tin cá nhân");
        user = FirebaseAuth.getInstance().getCurrentUser();
        email = DataLocalManager.getEmailLogin();
        DataTT.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.child("email").getValue().equals(email)) {
                        textViewName.setText("Hi, "+ds.child("fullName").getValue(String.class));
                        textViewEmail.setText(email);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    public void Init() {
        update_info_lin = findViewById(R.id.activity_profile_update);
        change_password_ln = findViewById(R.id.activity_change_pasword);
        textViewName = findViewById(R.id.welcome_name);
        textViewEmail = findViewById(R.id.textviewEmail);
        logout= findViewById(R.id.logout_layout);

    }

}