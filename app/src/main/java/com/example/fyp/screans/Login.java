package com.example.fyp.screans;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fyp.R;
import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    Button callSignUp, login_btn, login;
    ImageView img;
    TextView logotxt;
    TextInputLayout username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //Hooks
        callSignUp = findViewById(R.id.signup);
        img = findViewById(R.id.logo_img);
        logotxt = findViewById(R.id.logo_name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.loginBtn);

        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);

                Pair[] Pairs = new Pair[6];

                Pairs[0] = new Pair<View,String>(img, "logo_image");
                Pairs[1] = new Pair<View,String>( logotxt, "logo_text");
                Pairs[2] = new Pair<View,String>( username, "username_trans");
                Pairs[3] = new Pair<View,String>( password, "password_trans");
                Pairs[4] = new Pair<View,String>( login_btn, "button_trans");
                Pairs[5] = new Pair<View,String>( callSignUp, "login_signup-tran");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this,Pairs);

                startActivity(intent,options.toBundle());
            }
        });

        //Hooks
        login = findViewById(R.id.loginBtn);
        //opening the mainscreen page when the Login button clicked
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, MainScreen.class);
                startActivity(intent);
            }
        });
    }
}