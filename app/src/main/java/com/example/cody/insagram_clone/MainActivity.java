package com.example.cody.insagram_clone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.example.cody.insagram_clone.Utility.GradientBackgroundPainter;


public class MainActivity extends AppCompatActivity {
    GradientBackgroundPainter bgPainter;
    EditText userNameField;
    EditText passwordField;
    Button changeToSignUpMode;
    Button loginBtn;

    public void changeToSignUpMode(View view) {
        Log.i("tag", "hello");
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AVOSCloud.initialize(this, "3vPgWb164xS8KWWXurhWrqhQ-gzGzoHsz", "tVdPDtGB8ToHgK6luKANLgzO");
        setBgColor();
        userNameField = (EditText) findViewById(R.id.login_user);
        passwordField = (EditText) findViewById(R.id.login_pass);
        changeToSignUpMode = (Button) findViewById(R.id.btn_changetosignupmode);
        loginBtn = (Button) findViewById(R.id.btn_login);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVUser.logInInBackground(String.valueOf(userNameField.getText()), String.valueOf(passwordField.getText()), new LogInCallback<AVUser>() {
                    @Override
                    public void done(AVUser avUser, AVException e) {
                        if (avUser != null) {
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
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


}
