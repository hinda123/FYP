package com.example.fyp.util.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.fyp.R;
import com.example.fyp.model.club.ClubCategory;
import com.example.fyp.screans.ClubCategoryDetailScreen;
import com.example.fyp.util.api.FetchApi;

import java.util.List;

public class ClubCategoryAdepter extends ArrayAdapter<ClubCategory> {

    public ClubCategoryAdepter(@NonNull Context context, List<ClubCategory> clubCategories) {
        super(context, 0, clubCategories);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_custom_club_category, parent, false);
        ClubCategory clubCategory = getItem(position);
        DisplayUtil.setTextToTextview(clubCategory.getClubType(), R.id.clubCategoryTitle, convertView);
        DisplayUtil.networkImage(getContext(), convertView.findViewById(R.id.clubCategoryImageCover), FetchApi.getImageUrl(clubCategory.getImageIcon()));
        onClickCategory(convertView, clubCategory);
        return convertView;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void onClickCategory(@NonNull View convertView, ClubCategory clubCategory) {
        convertView.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ClubCategoryDetailScreen.class);
            intent.putExtra("id", clubCategory.getId());
            intent.putExtra("title", clubCategory.getClubType());
            intent.putExtra("imageUrl", clubCategory.getImageIcon());
            getContext().startActivity(intent);
        });
    }
}
