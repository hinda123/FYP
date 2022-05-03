package com.example.fyp.screans;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp.R;
import com.example.fyp.model.club.Club;
import com.example.fyp.util.TokenConfig;
import com.example.fyp.util.api.FetchApi;
import com.example.fyp.util.adapters.RecyclerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ClubCategoryDetailScreen extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private final TokenConfig tokenConfig = new TokenConfig(this);
    private Bundle bundle;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);
        bundle = getIntent().getExtras();
        Objects.requireNonNull(getSupportActionBar()).hide();
        changeHeader(bundle);
        recyclerView(bundle.getString("title"));
        drawer();
        navigate();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void recyclerView(String clubType) {
        RelativeLayout progress = findViewById(R.id.ccd_progress_con);
        FetchApi fetchApi = new FetchApi(new TokenConfig(this));

        fetchApi.get(
                "/clubs/club-infos?clubType=" + clubType,
                Club[].class,
                isLoading -> progress.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE),
                onResult,
                System.out::println
        );
    }

    private final Consumer<Club[]> onResult = clubs -> {
        RecyclerView recyclerView = findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerAdapter adapter = new RecyclerAdapter(this, bundle.getString("title"), Arrays.asList(clubs));
        recyclerView.setAdapter(adapter);
    };

    private void drawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navview);
        navigationView.bringToFront();
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_Open, R.string.close_menu);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private void changeHeader(Bundle bundle) {
        TextView titleTextView = findViewById(R.id.clubCategoryDetailTitle);
        titleTextView.setText(bundle.getString("title"));
        ImageView imageView = findViewById(R.id.clubCategoryDetailImage);
        String url  = FetchApi.getUrl("/events/media/download/file/"+bundle.getString("imageUrl"));
        Picasso.with(this).load(url).into(imageView);
    }


    @SuppressLint("NonConstantResourceId")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void navigate() {
        navigationView.setNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
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


                case R.id.nav_logout: //can change to id of the mainscreen or the home in menu navigation
                    Log.i("MENU_DRAWER_TAG", "logout item is clicked");
                    tokenConfig.removeToken();
                    finishAffinity();
                    startActivity(new Intent(getApplicationContext()
                            , Login.class));
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

            }
            return true;
        });
    }
}