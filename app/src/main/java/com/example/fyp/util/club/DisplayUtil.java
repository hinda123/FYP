package com.example.fyp.util.club;

import android.content.Context;
import android.text.Spanned;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class DisplayUtil {
    public static void networkImage(Context context, ImageView imageView, String url) {
        Picasso.with(context).load(url).into(imageView);
    }

    public static void setTextToTextview(String text, int id, AppCompatActivity activity) {
        TextView textView = activity.findViewById(id);
        textView.setText(text);
    }

    public static void setHTMLToTextview(Spanned spanned, int id, AppCompatActivity activity) {
        TextView textView = activity.findViewById(id);
        textView.setText(spanned);
    }
}
