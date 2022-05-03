package com.example.fyp.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Validation {

    public static void validate(TextInputLayout... inputs) {
        Arrays.stream(inputs).forEach(inputLayout -> inputLayout.setError(null));
        AtomicReference<Boolean> inValid = new AtomicReference<>(false);
        Arrays.stream(inputs).forEach(input -> {
            if (getTextFromTextInput(input).isEmpty()) {
                input.setError("Fill in this field");
                inValid.set(true);
            }
        });
        if(inValid.get())
            throw new RuntimeException("Validation error");
    }

    public static void validateEmail(TextInputLayout email) {
        Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(getTextFromTextInput(email));
        if (!matcher.find()) {
            email.setError("Enter valid email account");
            throw new RuntimeException("Validation error");
        }
    }

    public static void validatePassword(TextInputLayout password) {
        if (!isValidPassword(getTextFromTextInput(password))) {
            password.setError("Password must contain 8-20 length,(A-Z),(0-9),special characters");
            throw new RuntimeException("Validation error");
        }
    }

    private static boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        return password != null && Pattern.compile(regex).matcher(password).matches();
    }

    public static String getTextFromTextInput(TextInputLayout input) {
        return Objects.requireNonNull(input.getEditText()).getText().toString();
    }

}
