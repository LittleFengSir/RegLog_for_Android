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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


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

    public static class PasswordHashing{

        public static String hashPasswordWithSalt(String password, String salt) throws NoSuchAlgorithmException {
            String saltedPassword = password + salt;

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(saltedPassword.getBytes(StandardCharsets.UTF_8));

            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for(byte b : digest){
                sb.append(String.format("%02x",b & 0xff));
            }
            return sb.toString();
        }
        public static boolean verifyPassword(String password,String storedHash,String salt) throws NoSuchAlgorithmException{

            String hashedPassword = hashPasswordWithSalt(password,salt);

            return hashedPassword.equals(storedHash);
        }
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
                String query = "SELECT * FROM userInfo WHERE username = ?";
                String[] selectionArgs = {username};
                Cursor cursor = db.rawQuery(query,selectionArgs);
                String salt = null,storedHash = null;
                if(cursor.moveToFirst()){
                    int columnIndex = cursor.getColumnIndex("salt");
                    if(columnIndex != -1){
                        salt = cursor.getString(columnIndex);
                    } else {
                        Toast.makeText(this,"数据库数据丢失!",Toast.LENGTH_SHORT).show();
                    }
                    columnIndex = cursor.getColumnIndex("hash");
                    if(columnIndex != -1){
                        storedHash = cursor.getString(columnIndex);
                    }else{
                        Toast.makeText(this,"数据库数据丢失!",Toast.LENGTH_SHORT).show();
                    }

                    try {
                        if(PasswordHashing.verifyPassword(password,storedHash,salt)){
                            Toast.makeText(this,"登录成功！",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login_Activity.this, UserInfo_Activity.class);
                            intent.putExtra("username",username);
                            intent.putExtra("password",password);
                            startActivity(intent);
                        }
                    } catch (NoSuchAlgorithmException e) {
                        Toast.makeText(this,"登录失败！内部错误",Toast.LENGTH_SHORT).show();
                    }
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
