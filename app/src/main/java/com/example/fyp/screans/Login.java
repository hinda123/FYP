package com.example.fyp.screans;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp.R;
import com.example.fyp.model.Token;
import com.example.fyp.util.TokenConfig;
import com.example.fyp.util.Validation;
import com.example.fyp.util.api.FetchApi;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Map;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Login extends AppCompatActivity {

    private Button callSignUp, login_btn;
    protected ImageView img;
    private TextView logotxt;
    private TextInputLayout username, password;
    private final TokenConfig tokenConfig = new TokenConfig(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkIsLogin();
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        callSignUp = findViewById(R.id.signup);
        img = findViewById(R.id.logo_img);
        logotxt = findViewById(R.id.logo_name);
        username = findViewById(R.id.si_email);
        password = findViewById(R.id.si_password);
        login_btn = findViewById(R.id.si_loginBtn);
        goToSignUp();
        login();
    }

    private void checkIsLogin(){
      Token token = tokenConfig.getToken();
        System.out.println(token);
      if(token != null && !token.getAccessToken().isEmpty()){
          Intent intent = new Intent(Login.this, MainScreen.class);
          startActivity(intent);
          finish();
      }
    }

    private void login() {
        FetchApi fetchApi = new FetchApi(tokenConfig);
        login_btn.setOnClickListener(view -> {
            String email = Validation.getTextFromTextInput(this.username);
            String password = Validation.getTextFromTextInput(this.password);
            fetchApi.post(String.format("/auth/sign-in?email=%s&password=%s", email, password), null,
                    isLoading ->{},
                    token -> {
                        tokenConfig.saveToken(token);
                        Intent intent = new Intent(Login.this, MainScreen.class);
                        startActivity(intent);
                        finish();
                    },
                    System.out::println
            );
        });
    }



    private void goToSignUp() {
        callSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, SignUp.class);

            Pair[] Pairs = new Pair[6];

            Pairs[0] = new Pair<View, String>(img, "logo_image");
            Pairs[1] = new Pair<View, String>(logotxt, "logo_text");
            Pairs[2] = new Pair<View, String>(username, "username_trans");
            Pairs[3] = new Pair<View, String>(password, "password_trans");
            Pairs[4] = new Pair<View, String>(login_btn, "button_trans");
            Pairs[5] = new Pair<View, String>(callSignUp, "login_signup-tran");

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, Pairs);

            startActivity(intent, options.toBundle());
        });
    }
}