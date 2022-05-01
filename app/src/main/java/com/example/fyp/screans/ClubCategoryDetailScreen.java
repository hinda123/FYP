package com.example.fyp.screans;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fyp.R;
import com.example.fyp.util.club.RecyclerAdapter;
import com.example.fyp.service.ClubImp;
import com.example.fyp.service.ClubService;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.Objects;
import java.util.UUID;

public class ClubCategoryDetailScreen extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    Button callDesk;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ClubService clubService = new ClubImp();
        setContentView(R.layout.activity_sports);
        Bundle bundle = getIntent().getExtras();
        UUID id =(UUID) bundle.get("id");
        Objects.requireNonNull(getSupportActionBar()).hide();
        changeHeader(bundle);
        recyclerView(clubService, id);
        drawer();
        navigate();

    }
    private void recyclerView(ClubService clubService, UUID id) {
        RecyclerView recyclerView = findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerAdapter adapter = new RecyclerAdapter(this, clubService.listAllClubsByClubCategory(id.toString()));
        recyclerView.setAdapter(adapter);
    }

    private void drawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navview);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.menu_Open,R.string.close_menu);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void changeHeader(Bundle bundle) {
        TextView titleTextView = findViewById(R.id.clubCategoryDetailTitle);
        titleTextView.setText(bundle.getString("title"));
        ImageView imageView = findViewById(R.id.clubCategoryDetailImage);
        Picasso.with(this).load(bundle.getString("imageUrl")).into(imageView);
    }
    private void navigate() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext()
                                , MainScreen.class));
                        overridePendingTransition(0, 0);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_events:

                        startActivity(new Intent(getApplicationContext()
                                , EventScreen.class));
                        overridePendingTransition(0, 0);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_about:
                        startActivity(new Intent(getApplicationContext()
                                , AboutScreen.class));
                        overridePendingTransition(0, 0);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_login:
                        startActivity(new Intent(getApplicationContext()
                                , Login.class));
                        overridePendingTransition(0, 0);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_logout: //can change to id of the mainscreen or the home in menu navigation
                        Log.i("MENU_DRAWER_TAG", "logout item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }

                return true;
            }

            });
        }
    }
