package com.example.registor_login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserInfo_Activity extends AppCompatActivity implements
        View.OnClickListener{
    private String username,password;


    @SuppressLint("SetTextI20n")
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo_layout);
        findViewById(R.id.userInfo_btn_back).setOnClickListener(this);
        getDataFromLogin();
        String formattedString = String.format(getString(R.string.usernameInfo),username);
        TextView textView = findViewById(R.id.userInfo_username);
        textView.setText(formattedString);
        formattedString = String.format(getString(R.string.passwordInfo),password);
        textView = findViewById(R.id.userInfo_password);
        textView.setText(formattedString);
    }

    private void getDataFromLogin(){
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            username = bundle.getString("username");
        }
        if (bundle != null) {
            password = bundle.getString("password");
        }
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.userInfo_btn_back){
            finish();
        }
    }
}
