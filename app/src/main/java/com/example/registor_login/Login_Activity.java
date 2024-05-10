package com.example.registor_login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login_Activity extends AppCompatActivity implements
        View.OnClickListener{
    private EditText inputUsername,inputPassword;
    private String username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        findViewById(R.id.back_loginMenu).setOnClickListener(this);
        findViewById(R.id.btn_loginMenu).setOnClickListener(this);
    }

    private void Init(){
        inputUsername = findViewById(R.id.login_InputUsername);
        inputPassword = findViewById(R.id.login_InputPassword);
    }

    private void getData(){
        username = inputUsername.getText().toString().trim();
        password = inputPassword.getText().toString().trim();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_loginMenu){
            getData();
            if(TextUtils.isEmpty(username)){
                Toast.makeText(Login_Activity.this,"请输入账号",Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(Login_Activity.this,"请输入密码",Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.back_loginMenu) {
            finish();
        }
    }
}
