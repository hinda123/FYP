package com.example.fyp.screans;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp.R;
import com.example.fyp.service.ClubImp;
import com.example.fyp.service.ClubService;
import com.example.fyp.util.club.ClubCategoryAdepter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        GridView gridView = findViewById(R.id.clubCategoryGrid);
        ClubService clubService = new ClubImp();
        ClubCategoryAdepter clubCategoryAdepter = new ClubCategoryAdepter(this, clubService.listClubCategory());
        gridView.setAdapter(clubCategoryAdepter);
        bottomNavigator();

    }

    private void bottomNavigator() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.btnNav);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.home:
                    return true;
                case R.id.setting:
                    startActivity(new Intent(getApplicationContext()
                            , SettingScreen.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.events:
                    startActivity(new Intent(getApplicationContext()
                            , EventScreen.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.about:
                    startActivity(new Intent(getApplicationContext()
                            , AboutScreen.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;

        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

}





