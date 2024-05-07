package com.example.registor_login;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Register_Activity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        findViewById(R.id.btn_regBackMenu).setOnClickListener(this);
        findViewById(R.id.btn_regMenu).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_regMenu){

        } else if (v.getId() == R.id.btn_regBackMenu) {
            finish();
        }
    }
}
