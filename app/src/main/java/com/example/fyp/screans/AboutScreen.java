package com.example.fyp.screans;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp.R;
import com.example.fyp.util.adapters.DisplayUtil;

public class AboutScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_screen);
        DisplayUtil.bottomNavigator(this, R.id.about,-1);
    }
}