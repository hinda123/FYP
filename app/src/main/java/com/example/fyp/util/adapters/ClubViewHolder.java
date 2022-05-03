package com.example.fyp.util.adapters;

import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp.R;
import com.example.fyp.model.club.Club;
import com.example.fyp.util.api.FetchApi;

import java.util.function.BiConsumer;


public class ClubViewHolder extends RecyclerView.ViewHolder {
    private Club club;

    public ClubViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setClub(Club club) {
        View convertView = super.itemView;
        DisplayUtil.setTextToTextview(club.getTitle(), R.id.clubTitle, convertView);
        DisplayUtil.setTextToTextview("$" + club.getPrice(), R.id.clubPrice, convertView);
        DisplayUtil.setTextToTextview(club.getDate(), R.id.clubDate, convertView);
        DisplayUtil.networkImage(convertView.getContext(), convertView.findViewById(R.id.clubImage), FetchApi.getImageUrl(club.getImageCover()));
        this.club = club;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClick(BiConsumer<View, Club> onClickListener) {
        super.itemView.setOnClickListener(view -> onClickListener.accept(view, club));
    }


}
