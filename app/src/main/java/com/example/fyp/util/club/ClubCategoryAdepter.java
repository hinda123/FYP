package com.example.fyp.util.club;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fyp.R;
import com.example.fyp.model.club.ClubCategory;
import com.example.fyp.screans.ClubCategoryDetailScreen;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ClubCategoryAdepter extends ArrayAdapter<ClubCategory> {

    public ClubCategoryAdepter(@NonNull Context context, List<ClubCategory> clubCategories) {
        super(context, 0, clubCategories);
    }


    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_custom_club_category, parent, false);
        ClubCategory clubCategory = getItem(position);
        setClubTitle(clubCategory.getClubTitle(), convertView);
        setClubImageCover(clubCategory.getImageCover(), convertView);
        onClickCategory(convertView, clubCategory);
        return convertView;
    }

    private void onClickCategory(@NonNull View convertView, ClubCategory clubCategory) {
        convertView.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ClubCategoryDetailScreen.class);
            intent.putExtra("id", clubCategory.getId());
            intent.putExtra("title", clubCategory.getClubTitle());
            intent.putExtra("imageUrl", clubCategory.getImageCover());
            getContext().startActivity(intent);
        });
    }


    private void setClubImageCover(String imgCover, View cardView) {
        ImageView imageView = cardView.findViewById(R.id.clubCategoryImageCover);
        Picasso.with(cardView.getContext()).load(imgCover).into(imageView);
    }


    private void setClubTitle(String title, View cardView) {
        TextView textView = cardView.findViewById(R.id.clubCategoryTitle);
        textView.setText(title);
    }
}
