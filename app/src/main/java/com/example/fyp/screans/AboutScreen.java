package com.example.fyp.screans;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.fyp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AboutScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_screen);

        //initialize ans assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.btnNav);

        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.about);

        //perform item SelectedListner
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.about:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                , MainScreen.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.events:
                        startActivity(new Intent(getApplicationContext()
                                , EventScreen.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.setting:
                        startActivity(new Intent(getApplicationContext()
                                , SettingScreen.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

    }
}