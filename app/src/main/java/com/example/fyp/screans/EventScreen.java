package com.example.fyp.screans;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import com.example.fyp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EventScreen extends AppCompatActivity {
    private int initialScrollPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_screen);


        BottomNavigationView bottomNavigationView = findViewById(R.id.btnNav);
        showOrHideBottomNavigationView(bottomNavigationView);
        //initialize ans assign variable


        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.events);

        //perform item SelectedListner
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.events:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                , MainScreen.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.setting:
                        startActivity(new Intent(getApplicationContext()
                                , SettingScreen.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.about:
                        startActivity(new Intent(getApplicationContext()
                                , AboutScreen.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

    }

    private void showOrHideBottomNavigationView(BottomNavigationView bottomNavigationView) {
        ScrollView scrollView =findViewById(R.id.ev_scroller);
        initialScrollPosition = scrollView.getScrollY();
        scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            int scrollY = scrollView.getScrollY(); // For ScrollView
            if(initialScrollPosition > scrollY)
                bottomNavigationView.setVisibility(View.VISIBLE);
            else
                bottomNavigationView.setVisibility(View.INVISIBLE);
            initialScrollPosition = scrollY;
        });
    }
}