package com.example.fyp.screans;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.example.fyp.R;
import com.example.fyp.util.TokenConfig;
import com.example.fyp.util.adapters.DisplayUtil;

@RequiresApi(api = Build.VERSION_CODES.N)
public class SettingScreen extends AppCompatActivity {
    TokenConfig tokenConfig = new TokenConfig(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_screen);
        DisplayUtil.bottomNavigator(this, R.id.setting,R.id.se_container);
        Button logout = findViewById(R.id.se_logout_btn);
        logout.setOnClickListener(view -> {
            tokenConfig.removeToken();
            finishAffinity();
            startActivity(new Intent(getApplicationContext()
                    , Login.class));
        });
    }
}