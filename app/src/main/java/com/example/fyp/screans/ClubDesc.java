package com.example.fyp.screans;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp.R;
import com.example.fyp.model.club.Club;
import com.example.fyp.util.TokenConfig;
import com.example.fyp.util.api.FetchApi;
import com.example.fyp.util.club.DisplayUtil;

import java.util.UUID;
import java.util.function.Consumer;

public class ClubDesc extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_desc);
        getClub();

    }

    private void displayCLub(Club club) {
        DisplayUtil.networkImage(this, findViewById(R.id.cd_imageCover), club.getImageCover());
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


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getClub() {
        FetchApi fetchApi = new FetchApi(new TokenConfig(this));
        RelativeLayout progress = findViewById(R.id.cd_progress_con);
        UUID id = (UUID) getIntent().getExtras().get("id");
        fetchApi.get(
                "/clubs/club-info?id=" + id.toString(),
                Club.class,
                isLoading -> progress.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE),
                onResult,
                System.out::println
        );
    }

    private final Consumer<Club> onResult = club -> {
        joinToClub(club);
        displayCLub(club);
    };

    private void joinToClub(Club club) {
        Button joinClub = findViewById(R.id.cd_joinClub);
        joinClub.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), PaymentForm.class);
            intent.putExtra("id", club.getId());
            intent.putExtra("category", getIntent().getExtras().getString("category"));
            v.getContext().startActivity(intent);
        });
    }
}