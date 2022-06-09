package com.example.final_chat_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class verifypagee extends AppCompatActivity {
    TextView mchangenumber;
    EditText mgetotp;
    android.widget.Button mverifyotp;
    String enterdotp;
    FirebaseAuth firebaseAuth;
    ProgressBar mprogressbarverify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifypagee);
        mchangenumber=findViewById(R.id.changenumber);
        mgetotp=findViewById(R.id.editTextotp);
        mverifyotp=findViewById(R.id.verify_otp);
        mprogressbarverify=findViewById(R.id.verify_progressbar);
        firebaseAuth=FirebaseAuth.getInstance();
        mchangenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(verifypagee.this,MainActivity.class);
                startActivity(intent);
            }
        });
        mverifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterdotp=mgetotp.getText().toString();
                if(enterdotp.isEmpty()){
                    Toast.makeText(getApplicationContext(), "enter a otp", Toast.LENGTH_SHORT).show();
                }
                else{
                    mprogressbarverify.setVisibility(View.VISIBLE);
                    String coderecviever=getIntent().getStringExtra("otp");
                    PhoneAuthCredential credential= PhoneAuthProvider.getCredential(coderecviever,enterdotp);
                    signinwithphontcredentail(credential);
                }
            }
        });
    }
    private void signinwithphontcredentail(PhoneAuthCredential credential){
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mprogressbarverify.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "login sucess", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(verifypagee.this,MAIN.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                        Toast.makeText(getApplicationContext(), "login failed", Toast.LENGTH_SHORT).show();
                        mprogressbarverify.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }
}