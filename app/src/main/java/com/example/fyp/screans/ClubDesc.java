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
import com.example.fyp.util.adapters.DisplayUtil;

import java.util.UUID;
import java.util.function.Consumer;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ClubDesc extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_desc);
        getClub();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void displayCLub(Club club) {
        DisplayUtil.networkImage(this, findViewById(R.id.cd_imageCover),FetchApi.getImageUrl(club.getImageCover()));
        View view = findViewById(R.id.cd_container);
        DisplayUtil.setTextToTextview(club.getTitle(), R.id.cd_title,view);
        DisplayUtil.setTextToTextview(club.getDate(), R.id.cd_date,view);
        DisplayUtil.setTextToTextview("RM" + club.getPrice(), R.id.cd_price,view);
        DisplayUtil.setHTMLToTextview(Html.fromHtml(club.getDescription()), R.id.cd_desc,view);
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