package com.example.registor_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity implements
        View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //给按钮添加监听事件
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_register).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login){
            //记得在AndroidManifest.xml中注册Login_Activity.class
            Intent intent = new Intent(MainActivity.this, Login_Activity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_register) {
            Intent intent = new Intent(MainActivity.this, Register_Activity.class);
            startActivity(intent);
        }
    }
}