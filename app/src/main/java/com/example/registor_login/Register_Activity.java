package com.example.registor_login;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register_Activity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputUsername, inputPassword, inputPwdAgain;
    private String username, password, pwdAgain;

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[A-Za-z_0-9]{3,10}$");

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?:(?=.*[A-Za-z])(?=.*\\d)|(?=.*[A-Za-z])(?=.*[!@#$%^&*()_,.?\":{}|<>;\\[\\]\\\\/-])|(?=.*\\d)(?=.*[!@#$%^&*()_,.?\":{}|<>;\\[\\]\\\\/-])).{6,20}$");

    public static boolean isValidUsername(String username){
        Matcher matcher = USERNAME_PATTERN.matcher(username);
        return matcher.matches();
    }
    public static boolean isValidPassword(String password){
        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        return matcher.matches();
    }
    public boolean isUsernameExists(String username){
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM userInfo WHERE username = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.rawQuery(query,selectionArgs);
        if(cursor.moveToFirst()){
            cursor.close();
            db.close();
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }

    public boolean isPasswordEasy(String password){
        Zxcvbn zxcvbn = new Zxcvbn();
        Strength strength = zxcvbn.measure(password);
        return strength.getScore() < 2;
    }



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
            MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("username",username);
            values.put("password",password);
            db.insert("userInfo",null,values);
            db.close();
            Toast.makeText(Register_Activity.this, "注册成功！", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Register_Activity.this, "账号不能为空", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(Register_Activity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(pwdAgain)) {
                Toast.makeText(Register_Activity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
            } else if (!TextUtils.equals(password, pwdAgain)) {
                Toast.makeText(Register_Activity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
            } else if (!isValidUsername(username)) {
                Toast.makeText(Register_Activity.this,"请输入3-10位的用户名，且不要包含特殊字符",Toast.LENGTH_SHORT).show();
            } else if (!isValidPassword(password))  {
                Toast.makeText(Register_Activity.this,"密码可包含字母，数字，特殊字符(除中文字符和空格),且密码长度在6-20位",Toast.LENGTH_SHORT).show();
            } else if (isUsernameExists(username)) {
                Toast.makeText(Register_Activity.this,"该账号已存在，请重新输入",Toast.LENGTH_SHORT).show();
            } else if (isPasswordEasy(password)) {
                Toast.makeText(Register_Activity.this,"密码过于简单",Toast.LENGTH_SHORT).show();
            } else {
                MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                String query = "SELECT * FROM userInfo WHERE username = ?";
                String[] selectionArgs = {username};
                Cursor cursor = db.rawQuery(query,selectionArgs);
                if(cursor.moveToFirst()){
                    Toast.makeText(this,"账号已存在，请换账号",Toast.LENGTH_SHORT).show();
                    cursor.close();
                    db.close();
                } else {
                    cursor.close();
                    db.close();
                    showAlertDialog();
                }
            }

        } else if (v.getId() == R.id.btn_regBackMenu) {
            finish();
        }
    }
}