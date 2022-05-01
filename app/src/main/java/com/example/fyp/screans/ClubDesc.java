package com.example.fyp.screans;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp.R;
import com.example.fyp.model.club.Club;
import com.example.fyp.util.club.ClubServiceUtil;
import com.example.fyp.util.club.DisplayUtil;

import java.util.UUID;

public class ClubDesc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_desc);
        Club club = getClub();
        displayCLub(club);
        joinToClub(club);
    }

    private void displayCLub(Club club) {

        DisplayUtil.networkImage(this, findViewById(R.id.cd_imageCover), club.getImageIcon());
        setText(club.getTitle(), findViewById(R.id.cd_title));
        setText(club.getDate(), findViewById(R.id.cd_date));
        setText("RM" + club.getPrice(), findViewById(R.id.cd_price));
        setHtml(Html.fromHtml(club.getDescription()), findViewById(R.id.cd_desc));
    }

    private void setText(String text, TextView textView) {
        textView.setText(text);
    }

    private void setHtml(Spanned spanned, TextView textView) {
        textView.setText(spanned);
    }


    private Club getClub() {
        UUID id = (UUID) getIntent().getExtras().get("id");
        return ClubServiceUtil.createClubService().findClubById(id);
    }

    private void joinToClub(Club club) {
        Button joinClub = findViewById(R.id.cd_joinClub);
        joinClub.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), PaymentForm.class);
            intent.putExtra("id",club.getId());
            v.getContext().startActivity(intent);
        });
    }
}