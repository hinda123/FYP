package com.example.fyp.util.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fyp.R;
import com.example.fyp.screans.AboutScreen;
import com.example.fyp.screans.EventScreen;
import com.example.fyp.screans.MainScreen;
import com.example.fyp.screans.SettingScreen;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.concurrent.atomic.AtomicInteger;

public class DisplayUtil {

    public static void networkImage(Context context, ImageView imageView, String url) {
        Picasso.with(context).load(url).into(imageView);
    }

    public static void setTextToTextview(String text, int id, View view) {
        TextView textView = view.findViewById(id);
        textView.setText(text);
    }

    public static void setHTMLToTextview(Spanned spanned, int id, View view) {
        TextView textView = view.findViewById(id);
        textView.setText(spanned);
    }

    @SuppressLint("NonConstantResourceId")
    public static void bottomNavigator(Activity activity, int screenId, int scrollId) {
        BottomNavigationView bottomNavigationView = activity.findViewById(R.id.btnNav);
        if(scrollId >= 0)
         showOrHideBottomNavigationView(bottomNavigationView,scrollId,activity);
        bottomNavigationView.setSelectedItemId(screenId);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    activity.startActivity(new Intent(activity.getApplicationContext()
                            , MainScreen.class));
                    activity.overridePendingTransition(0, 0);
                    return true;
                case R.id.setting:
                    activity.startActivity(new Intent(activity.getApplicationContext()
                            , SettingScreen.class));
                    activity.overridePendingTransition(0, 0);
                    return true;
                case R.id.events:
                    activity.startActivity(new Intent(activity.getApplicationContext()
                            , EventScreen.class));
                    activity.overridePendingTransition(0, 0);
                    return true;
                case R.id.about:
                    activity.startActivity(new Intent(activity.getApplicationContext()
                            , AboutScreen.class));
                    activity.overridePendingTransition(0, 0);
                    return true;
            }
            return false;

        });
    }


    private static void showOrHideBottomNavigationView(BottomNavigationView bottomNavigationView, int scrollId, Activity activity) {
        View scrollView = activity.findViewById(scrollId);
       final AtomicInteger initialScrollPosition = new AtomicInteger(scrollView.getScrollY());
        scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            int scrollY = scrollView.getScrollY();
            if (initialScrollPosition.get() > scrollY)
                bottomNavigationView.setVisibility(View.VISIBLE);
            else
                bottomNavigationView.setVisibility(View.INVISIBLE);
            initialScrollPosition.set(scrollY);
        });
    }


}
