package com.example.fyp.screans;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.fyp.R;
import com.example.fyp.model.club.Club;
import com.example.fyp.model.club.ClubCheckout;
import com.example.fyp.util.club.ClubServiceUtil;
import com.example.fyp.util.club.DisplayUtil;

import java.util.UUID;

public class PaymentForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_form);
        ClubCheckout club = getClub();
        displayCheckout(club);
    }

    private void displayCheckout(ClubCheckout clubCheckout){
        Club club = clubCheckout.getClub();
        DisplayUtil.networkImage(this,findViewById(R.id.cp_imageCover),club.getImageIcon());
        DisplayUtil.setTextToTextview(clubCheckout.getClubCategory(),R.id.cp_clubCategory,this);
        DisplayUtil.setTextToTextview(club.getTitle(),R.id.cp_clubTitle,this);
        DisplayUtil.setTextToTextview(club.getDate(),R.id.cp_clubDate,this);
        DisplayUtil.setTextToTextview("RM"+club.getPrice(),R.id.cp_clubPrice,this);
    }


    private ClubCheckout getClub() {
        UUID id = (UUID) getIntent().getExtras().get("id");
        return ClubServiceUtil.createClubService().findClubToCheckout(id);
    }
}