package com.example.registor_login;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
        Init();
    }

    private void Init(){
        inputUsername = findViewById(R.id.login_InputUsername);
        inputPassword = findViewById(R.id.login_InputPassword);
    }

    private void getData(){
        username = inputUsername.getText().toString();
        password = inputPassword.getText().toString();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_loginMenu){
            getData();
            if(TextUtils.isEmpty(username)){
                Toast.makeText(Login_Activity.this,"请输入账号",Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(Login_Activity.this,"请输入密码",Toast.LENGTH_SHORT).show();
            }else {
                MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                String query = "SELECT * FROM userInfo WHERE username = ? AND password = ?";
                String[] selectionArgs = {username,password};
                Cursor cursor = db.rawQuery(query,selectionArgs);

                if(cursor.moveToFirst()){
                    Toast.makeText(this,"登录成功！",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login_Activity.this, UserInfo_Activity.class);
                    intent.putExtra("username",username);
                    intent.putExtra("password",password);
                    startActivity(intent);
                }else{
                    Toast.makeText(this,"账号或密码错误！",Toast.LENGTH_SHORT).show();
                }
                cursor.close();
                db.close();
            }
        } else if (v.getId() == R.id.back_loginMenu) {
            finish();
        }
    }
}
