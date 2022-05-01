package com.example.fyp.util.club;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp.R;
import com.example.fyp.model.club.Club;
import com.example.fyp.screans.ClubCategoryDetailScreen;
import com.example.fyp.screans.ClubDesc;
import com.example.fyp.screans.Login;
import com.example.fyp.screans.MainScreen;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter <ClubViewHolder>{
    private final List<Club> clubs;
    private final Context context;

    public RecyclerAdapter(Context context,List<Club> clubs) {
        this.clubs = clubs;
        this.context = context;
    }
    @NonNull
    @Override
    public ClubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_design,parent, false);
        return new ClubViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ClubViewHolder holder, int position) {
        holder.setClub(clubs.get(position));
        holder.onClick((view, club) -> {
            Toast.makeText(context, ""+club.getTitle(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(view.getContext(), ClubDesc.class);
            intent.putExtra("id", club.getId());
            view.getContext().startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return clubs.size();
    }

}
