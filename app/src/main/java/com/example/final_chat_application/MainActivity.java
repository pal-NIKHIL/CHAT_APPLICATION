package com.example.final_chat_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    EditText getphonenumber;
    android.widget.Button otp_button;
    CountryCodePicker codePicker;
    String countrycode;
    String phoneumber;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBarofmain;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks;
    String codesent;
    EditText getaaharnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        codePicker = findViewById(R.id.ccp);
        otp_button = findViewById(R.id.button_signin);
        getphonenumber = findViewById(R.id.user_name);
        getaaharnumber=findViewById(R.id.aadhar_name);
        progressBarofmain = findViewById(R.id.profile_progressbar);
        firebaseAuth = FirebaseAuth.getInstance();
        countrycode = codePicker.getSelectedCountryCodeWithPlus();
        codePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countrycode = codePicker.getSelectedCountryCodeWithPlus();
            }
        });
        otp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number,number1;
                number = getphonenumber.getText().toString();
                number1=getaaharnumber.getText().toString();

                if (number.isEmpty() && number1.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter number", Toast.LENGTH_SHORT).show();
                } else {
                    progressBarofmain.setVisibility(View.VISIBLE);
                    phoneumber = countrycode + number;
                    PhoneAuthOptions options =
                            PhoneAuthOptions.newBuilder(firebaseAuth)
                                    .setPhoneNumber(phoneumber)        // Phone number to verify
                                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                    .setActivity(MainActivity.this)                 // Activity (for callback binding)
                                    .setCallbacks(mcallbacks)          // OnVerificationStateChangedCallbacks
                                    .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
                }
                if(number1.length()<12){
                    Toast.makeText(getApplicationContext(),"Invalid aadhar Number",Toast.LENGTH_SHORT).show();
                }
            }

        });
        mcallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(getApplicationContext(), "code is sent", Toast.LENGTH_SHORT).show();
                progressBarofmain.setVisibility(View.INVISIBLE);
                codesent = s;
                Intent intent=new Intent(MainActivity.this,verifypagee.class);
                intent.putExtra("otp",codesent);
                startActivity(intent);
            }

        };
    }
    @Override
    protected void onStart(){
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            Intent intent=new Intent(MainActivity.this,MAIN.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}