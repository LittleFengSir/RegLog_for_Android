package com.example.registor_login;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Login_Activity extends AppCompatActivity implements
        View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        findViewById(R.id.back_loginMenu).setOnClickListener(this);
        findViewById(R.id.btn_loginMenu).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_loginMenu){

        } else if (v.getId() == R.id.back_loginMenu) {
            finish();
        }
    }
}
