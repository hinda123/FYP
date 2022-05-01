package com.example.fyp.util.club;

import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp.R;
import com.example.fyp.model.club.Club;

import java.util.function.BiConsumer;


public class ClubViewHolder extends RecyclerView.ViewHolder {
    private Club club;

    public ClubViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void setClub(Club club) {
        View convertView = super.itemView;
        setText(club.getTitle(), convertView.findViewById(R.id.clubTitle));
        setText("$" + club.getPrice(), convertView.findViewById(R.id.clubPrice));
        setText(club.getDate(), convertView.findViewById(R.id.clubDate));
        DisplayUtil.networkImage(convertView.getContext(),convertView.findViewById(R.id.clubImage),club.getImageIcon());
        this.club = club;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClick(BiConsumer<View, Club> onClickListener) {
        super.itemView.setOnClickListener(view -> onClickListener.accept(view, club));
    }

    private void setText(String text, TextView view) {
        view.setText(text);
    }


}
