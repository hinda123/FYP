package com.example.fyp.util.adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp.R;
import com.example.fyp.model.event.Event;

import java.util.List;
@RequiresApi(api = Build.VERSION_CODES.N)
public class EventAdepter extends RecyclerView.Adapter<EventViewHolder> {
    private final List<Event> events;

    public EventAdepter(List<Event> events) {
        this.events = events;
    }


    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_custom_design_event, parent, false);
        return new EventViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.setEvent(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
