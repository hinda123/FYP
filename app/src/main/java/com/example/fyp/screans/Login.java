package com.example.fyp.screans;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp.R;
import com.example.fyp.util.Validation;
import com.example.fyp.util.api.FetchApi;
import com.google.android.material.textfield.TextInputLayout;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Login extends AppCompatActivity {

    private Button callSignUp, login_btn;
    protected ImageView img;
    private TextView logotxt;
    private TextInputLayout username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    private void login() {
        login_btn.setOnClickListener(view -> {
            String email = Validation.getTextFromTextInput(this.username);
            String password = Validation.getTextFromTextInput(this.password);
            FetchApi.post(String.format("/auth/sign-in?email=%s&password=%s", email, password), null,
                    isLoading -> {
                    },
                    token -> {
                        Intent intent = new Intent(Login.this, MainScreen.class);
                        startActivity(intent);
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