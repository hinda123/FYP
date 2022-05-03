package com.example.fyp.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.fyp.model.Token;
import com.example.fyp.util.api.FetchApi;

import java.util.Objects;
@RequiresApi(api = Build.VERSION_CODES.N)
public class TokenConfig {

    private final Context context;

    public TokenConfig(Context context) {
        this.context = context;
    }


    public void saveToken(String jwt){
        Token token = Objects.requireNonNull(FetchApi.jsonToObject(jwt,Token.class));
        SharedPreferences sharedpreferences = context.getSharedPreferences("jwt_token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("accessToken",token.getAccessToken());
        editor.putString("refreshToken",token.getRefreshToken());
        editor.apply();
    }

    public void removeToken(){
        SharedPreferences sharedpreferences = context.getSharedPreferences("jwt_token", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear().commit();
    }

    public Token getToken(){
        SharedPreferences sharedpreferences = context.getSharedPreferences("jwt_token", Context.MODE_PRIVATE);
        String accessToken = sharedpreferences.getString("accessToken", "");
        String refreshToken = sharedpreferences.getString("refreshToken", "");
        return new Token(accessToken,refreshToken);
    }

    public Context getContext() {
        return context;
    }
}
