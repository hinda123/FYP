package com.example.fyp.screans;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp.R;
import com.example.fyp.model.club.ClubCategory;
import com.example.fyp.util.TokenConfig;
import com.example.fyp.util.api.FetchApi;
import com.example.fyp.util.club.ClubCategoryAdepter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.function.Consumer;

public class MainScreen extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        getClubCategory();
        bottomNavigator();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getClubCategory() {
        RelativeLayout progressBar = findViewById(R.id.progress_con);
        FetchApi fetchApi = new FetchApi(new TokenConfig(this));

        fetchApi.get("/clubs/main-clubs", ClubCategory[].class,
                isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE),
                onResult, System.out::println);
    }

    private final Consumer<ClubCategory[]> onResult = result -> {
        GridView gridView = findViewById(R.id.clubCategoryGrid);
        ClubCategoryAdepter clubCategoryAdepter = new ClubCategoryAdepter(this, Arrays.asList(result));
        gridView.setAdapter(clubCategoryAdepter);
    };


    @SuppressLint("NonConstantResourceId")
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





