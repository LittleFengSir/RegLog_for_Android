package com.example.registor_login;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Register_Activity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputUsername, inputPassword, inputPwdAgain;
    private String username, password, pwdAgain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        findViewById(R.id.btn_regBackMenu).setOnClickListener(this);
        findViewById(R.id.btn_regMenu).setOnClickListener(this);

        init();
    }

    private void init() {
        //获取界面控件
        inputUsername = findViewById(R.id.regInput_username);
        inputPassword = findViewById(R.id.regInput_password);
        inputPwdAgain = findViewById(R.id.regInput_pwdAgain);

    }

    private void getData() {
        username = inputUsername.getText().toString();
        password = inputPassword.getText().toString();
        pwdAgain = inputPwdAgain.getText().toString();
    }


    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Register_Activity.this);

        builder.setTitle("用户信息确认")
                .setMessage("账号：" + username + "\n" + "密码：" + password);
        builder.setPositiveButton("确定", (dialog, which) -> {
            Toast.makeText(Register_Activity.this, "您点击了确认按钮", Toast.LENGTH_SHORT).show();

            MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("username",username);
            values.put("password",password);
            db.insert("userInfo",null,values);
            db.close();
            dialog.cancel();
        });
        builder.setNegativeButton("取消", (dialog, which) -> {
            Toast.makeText(Register_Activity.this, "您点击了取消按钮", Toast.LENGTH_SHORT).show();
            dialog.cancel();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_regMenu) {
            getData();
            if (TextUtils.isEmpty(username)) {
                Toast.makeText(Register_Activity.this, "请输入账号", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(Register_Activity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(pwdAgain)) {
                Toast.makeText(Register_Activity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
            } else if (!TextUtils.equals(password, pwdAgain)) {
                Toast.makeText(Register_Activity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
            } else {
                showAlertDialog();
            }

        } else if (v.getId() == R.id.btn_regBackMenu) {
            finish();
        }


    }
}