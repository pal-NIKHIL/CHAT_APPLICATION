package com.example.final_chat_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class chat_page1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page1);
    }

    public void Back(View view) {
        Intent intent=new Intent(chat_page1.this,MAIN.class);
        startActivity(intent);
        finish();
    }
}