package com.example.fyp.screans;

import static com.example.fyp.util.Validation.getTextFromTextInput;
import static com.example.fyp.util.Validation.validate;
import static com.example.fyp.util.Validation.validateEmail;
import static com.example.fyp.util.Validation.validatePassword;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp.R;
import com.example.fyp.model.student.Student;
import com.example.fyp.util.TokenConfig;
import com.example.fyp.util.api.FetchApi;
import com.google.android.material.textfield.TextInputLayout;


@RequiresApi(api = Build.VERSION_CODES.N)
public class SignUp extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        onSignIn();
        onSignUp();
    }

    private Student readForm() {
        TextInputLayout fullName = findViewById(R.id.su_fullName), studentID = findViewById(R.id.su_studentId),
                faculty = findViewById(R.id.su_faculty), email = findViewById(R.id.su_email), password = findViewById(R.id.su_password);
        validateFields(fullName, studentID, faculty, email, password);
        return new Student(null, getTextFromTextInput(fullName), getTextFromTextInput(studentID), getTextFromTextInput(faculty),
                getTextFromTextInput(email), getTextFromTextInput(password));


    }

    private void validateFields(TextInputLayout fullName, TextInputLayout studentID, TextInputLayout faculty, TextInputLayout email, TextInputLayout password) {
        validate(fullName, studentID, faculty, email, password);
        validateEmail(email);
        validatePassword(password);
    }


    private void onSignUp() {
        Button signUp = findViewById(R.id.su_signupBtn);
        FetchApi fetchApi = new FetchApi(new TokenConfig(this));
        signUp.setOnClickListener(view -> {
            try {
                Student student = readForm();
             fetchApi.post("/auth/sign-up", student,
                        isLoading ->{},
                        response -> {
                            Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                            goToLogin();
                        }, onError -> Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT).show());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }


    private void onSignIn() {
        Button signIn = findViewById(R.id.back_login);
        signIn.setOnClickListener(view -> goToLogin());
    }

    private void goToLogin() {
        Intent intent = new Intent(SignUp.this, Login.class);
        startActivity(intent);
    }
}
