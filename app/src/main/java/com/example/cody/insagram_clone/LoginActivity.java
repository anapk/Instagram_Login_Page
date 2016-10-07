package com.example.cody.insagram_clone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.example.cody.insagram_clone.Utility.GradientBackgroundPainter;


public class LoginActivity extends Activity implements View.OnKeyListener {
    GradientBackgroundPainter bgPainter;
    EditText userNameField;
    EditText passwordField;
    Button loginBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();
        //若用户已登录，直接进入userlist
        if (AVUser.getCurrentUser() != null) {
            showUserList();
        }



    }

    private void initialize() {
        AVOSCloud.initialize(this,
                "3vPgWb164xS8KWWXurhWrqhQ-gzGzoHsz",
                "tVdPDtGB8ToHgK6luKANLgzO");
        setBgColor();
        userNameField = (EditText) findViewById(R.id.login_user);
        passwordField = (EditText) findViewById(R.id.login_pass);
        loginBtn = (Button) findViewById(R.id.btn_login);
        userNameField.setOnKeyListener(this);
        passwordField.setOnKeyListener(this);


    }


    public void login(View view) {
        String username = String.valueOf(userNameField.getText());
        String password = String.valueOf(passwordField.getText());

        if (username.isEmpty()) {
            Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (avUser != null) {
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    showUserList();
                } else {
                    String[] error = e.getMessage().split(":");
                    Toast.makeText(getApplicationContext(), error[2].replace("}", "").replace("\"", ""), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

        });
    }

    private void showUserList() {
        startActivity(new Intent(getApplicationContext(), UserList.class));
    }

    public void dismissKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }


    public void changeToSignUpMode(View view) {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
    }

    private void setBgColor() {
        View bgBottom = findViewById(R.id.btn_changetosignupmode);
        View bgImage = findViewById(R.id.root_view);
        final int[] drawables = new int[4];
        drawables[0] = R.drawable.gradient_1;
        drawables[1] = R.drawable.gradient_2;
        drawables[2] = R.drawable.gradient_3;
        drawables[3] = R.drawable.gradient_4;
        drawables[3] = R.drawable.gradient_5;
        bgPainter = new GradientBackgroundPainter(bgImage, drawables);
        bgPainter.start();
        bgPainter = new GradientBackgroundPainter(bgBottom, drawables);
        bgPainter.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bgPainter.stop();

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            login(v);
        }
        return false;
    }
}
