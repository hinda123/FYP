package com.example.fyp.util.adapters;

import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp.R;
import com.example.fyp.model.event.Event;
import com.example.fyp.util.api.FetchApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class EventViewHolder extends RecyclerView.ViewHolder {

    public EventViewHolder(@NonNull View itemView) {
        super(itemView);
    }


    public void setEvent(Event event) {
        View convertView = super.itemView;
        DisplayUtil.networkImage(convertView.getContext(), convertView.findViewById(R.id.ev_imageCover), FetchApi.getImageUrl(event.getMedia()));
        DisplayUtil.setTextToTextview(event.getTitle(), R.id.ev_title, convertView);
        DisplayUtil.setTextToTextview(event.getDescription(), R.id.ev_desc, convertView);
        DisplayUtil.setTextToTextview(event.getPostedAt(), R.id.ev_date, convertView);
    }





}
