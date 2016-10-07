package com.example.cody.insagram_clone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.example.cody.insagram_clone.Utility.GradientBackgroundPainter;

public class SignUpActivity extends Activity implements View.OnKeyListener{
    GradientBackgroundPainter bgPainter;
    private EditText userNameFieldSu;
    private EditText passwordFieldSu;
    private Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initialize();
        signUp();
    }

    private void initialize() {
        setBgColor();
        userNameFieldSu = (EditText) findViewById(R.id.signup_user);
        passwordFieldSu = (EditText) findViewById(R.id.signup_pass);
        signUpBtn = (Button) findViewById(R.id.btn_signup);
    }

    private void signUp() {
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVUser user = new AVUser();
                String username = String.valueOf(userNameFieldSu.getText());
                String password = String.valueOf(passwordFieldSu.getText());
                if (username.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                user.setUsername(String.valueOf(userNameFieldSu.getText()));
                user.setPassword(String.valueOf(passwordFieldSu.getText()));
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            Toast.makeText(SignUpActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            Log.i("Login Info", "SignUp Successful");
                        } else {
                            String[] error = e.getMessage().split(":");
                            Toast.makeText(getApplicationContext(), error[2].replace("}", "").replace("\"", ""), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }

    public void changeToLoginMode(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            signUp();
        }
        return false;
    }

    public void dismissKeyboardSu(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
    private void setBgColor() {
        View bgBottom = findViewById(R.id.btn_changetologinmode);
        View bgImage = findViewById(R.id.signup_view);
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


}
