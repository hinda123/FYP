package com.example.fyp.screans;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp.R;
import com.example.fyp.model.event.Event;
import com.example.fyp.util.TokenConfig;
import com.example.fyp.util.adapters.DisplayUtil;
import com.example.fyp.util.adapters.EventAdepter;
import com.example.fyp.util.api.FetchApi;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.function.Consumer;

public class EventScreen extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_screen);
        DisplayUtil.bottomNavigator(this, R.id.events,R.id.ev_scroller);
        fetchEvents();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void fetchEvents() {
        FetchApi fetchApi = new FetchApi(new TokenConfig(this));
        fetchApi.get("/events", Event[].class, System.out::println, onResult, System.out::println);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private final Consumer<Event[]> onResult = result -> {
        RecyclerView recyclerView = findViewById(R.id.ev_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        EventAdepter eventAdepter = new EventAdepter(Arrays.asList(result));
        recyclerView.setAdapter(eventAdepter);
    };


}