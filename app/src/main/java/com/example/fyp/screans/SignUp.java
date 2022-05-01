package com.example.fyp.screans;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fyp.R;

public class SignUp extends AppCompatActivity {


    Button callLogin, callLMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Hooks
        callLogin = findViewById(R.id.back_login);

        //going back to the login screen when clicked the new member button
        callLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });
        //Hooks
        callLMain = findViewById(R.id.register);

        //opening the main page when the register button clicked
        callLMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, MainScreen.class);
                startActivity(intent);
            }
        });
    }
}
