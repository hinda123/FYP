package com.example.fyp.screans;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp.R;
import com.example.fyp.model.club.Club;
import com.example.fyp.model.club.ClubCheckout;
import com.example.fyp.util.TokenConfig;
import com.example.fyp.util.api.FetchApi;
import com.example.fyp.util.club.ClubServiceUtil;
import com.example.fyp.util.club.DisplayUtil;

import java.util.UUID;

public class PaymentForm extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_form);
        getClub();
    }

    private void displayCheckout(Club club) {
        DisplayUtil.networkImage(this, findViewById(R.id.cp_imageCover), club.getImageCover());
        DisplayUtil.setTextToTextview(getIntent().getExtras().getString("category"), R.id.cp_clubCategory, this);
        DisplayUtil.setTextToTextview(club.getTitle(), R.id.cp_clubTitle, this);
        DisplayUtil.setTextToTextview(club.getDate(), R.id.cp_clubDate, this);
        DisplayUtil.setTextToTextview("RM" + club.getPrice(), R.id.cp_clubPrice, this);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getClub() {
        UUID id = (UUID) getIntent().getExtras().get("id");
        FetchApi fetchApi = new FetchApi(new TokenConfig(this));

        fetchApi.get(
                "/clubs/club-info-wt-desc?id=" + id.toString(),
                Club.class,
                isLoading -> {
                },
                this::displayCheckout,
                System.out::println
        );

    }
}