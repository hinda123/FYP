package com.example.fyp.screans;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp.R;
import com.example.fyp.model.club.Club;
import com.example.fyp.util.TokenConfig;
import com.example.fyp.util.adapters.DisplayUtil;
import com.example.fyp.util.api.FetchApi;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.UUID;

@RequiresApi(api = Build.VERSION_CODES.N)
public class PaymentForm extends AppCompatActivity {

    public static final String clientKey = "ASMWQje3tVE9hGni1cPhtfDXT4wTE2iURlB8kUJMPHB3XQO9bblqwScRHBbUjDd3ruRvUmL1sZx1rIUY";
    private final TokenConfig tokenConfig = new TokenConfig(this);
    public static final int PAYPAL_REQUEST_CODE = 123;
    private Button payWithPaypal, payWithCash;

    // Paypal Configuration Object
    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment. When ready,
            // switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            // on below line we are passing a client id.
            .clientId(clientKey);

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_form);
        getClub();
        payWithPaypal = findViewById(R.id.cp_payWithPaypal);
        payWithCash = findViewById(R.id.cp_payWithCash);


        payWithPaypal.setOnClickListener(view -> {
            getPayment();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void displayCheckout(Club club) {
        View view = findViewById(R.id.cp_container);
        DisplayUtil.networkImage(this, findViewById(R.id.cp_imageCover), FetchApi.getImageUrl(club.getImageCover()));
        DisplayUtil.setTextToTextview(getIntent().getExtras().getString("category"), R.id.cp_clubCategory, view);
        DisplayUtil.setTextToTextview(club.getTitle(), R.id.cp_clubTitle, view);
        DisplayUtil.setTextToTextview(club.getDate(), R.id.cp_clubDate, view);
        DisplayUtil.setTextToTextview("RM" + club.getPrice(), R.id.cp_clubPrice, view);

        payWithCash.setOnClickListener(btnView -> {
            FetchApi fetchApi = new FetchApi(tokenConfig);
            fetchApi.post(
                    "/clubs/club-info/join/on-cash?clubId=" + club.getId(),
                    null,
                    onLoading -> {
                    },
                    result -> {
                        payWithCash.setVisibility(View.INVISIBLE);
                        payWithPaypal.setVisibility(View.INVISIBLE);
                        new AlertDialog.Builder(this)
                                .setTitle("Note")
                                .setMessage(result + ", please pay the money in 48 hours to the office. if you fail to do so you'll removed from the club list.")
                                .setNegativeButton(android.R.string.ok, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    },
                    System.out::println
            );
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getClub() {
        UUID id = (UUID) getIntent().getExtras().get("id");
        FetchApi fetchApi = new FetchApi(new TokenConfig(this));
        fetchApi.get(
                "/clubs/club-info-wt-desc?id=" + id.toString(),
                Club.class,
                isLoading -> {
                },
                this::displayCheckout,
                System.out::println
        );
    }

    private void getPayment() {
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(21)), "USD", "Course Fees",
                PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {

            // If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {

                // Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                // if confirmation is not null
                if (confirm != null) {
                    try {
                        // Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        // on below line we are extracting json response and displaying it in a text view.
                        JSONObject payObj = new JSONObject(paymentDetails);
                        String payID = payObj.getJSONObject("response").getString("id");
                        String state = payObj.getJSONObject("response").getString("state");
                        System.out.println(payID);
                        System.out.println(state);
                    } catch (JSONException e) {
                        // handling json exception on below line
                        Log.e("Error", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // on below line we are checking the payment status.
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                // on below line when the invalid paypal config is submitted.
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }


}