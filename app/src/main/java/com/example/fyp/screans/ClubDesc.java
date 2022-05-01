package com.example.fyp.screans;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp.PaymentForm;
import com.example.fyp.R;

public class ClubDesc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_desc);
        joinToClub();
    }

    private void joinToClub() {
        Button joinClub = findViewById(R.id.cd_joinClub);
        joinClub.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), PaymentForm.class);
            v.getContext().startActivity(intent);
        });
    }
}