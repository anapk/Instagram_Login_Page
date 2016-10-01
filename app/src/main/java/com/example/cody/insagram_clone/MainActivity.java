package com.example.cody.insagram_clone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.example.cody.insagram_clone.Utility.GradientBackgroundPainter;


public class MainActivity extends AppCompatActivity {
    GradientBackgroundPainter bgPainter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bgPainter.stop();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AVOSCloud.initialize(this, "3vPgWb164xS8KWWXurhWrqhQ-gzGzoHsz", "tVdPDtGB8ToHgK6luKANLgzO");
        View bgBottom=findViewById(R.id.bottom_text);
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




 /*       AVUser user = new AVUser();
        user.setUsername("codyooo");
        user.setPassword("password");
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.i("TAG", "sign up sucessful");
                } else{
                    e.printStackTrace();
                    Log.i("TAG", "sign up failed");
                }
            }
        });*/


/*        AVUser.logInInBackground("codyooo", "password", new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {

                if (avUser != null) {
                    Log.i("TAG", "Successful");
                } else {
                    Log.i("TAG", "Failed");
                    e.printStackTrace();
                }

            }
        });*/


        if (AVUser.getCurrentUser() != null) {
            Log.i("TAG", "User logged in ");

        } else {
            Log.i("TAG", "not logged in");
        }

        AVAnalytics.trackAppOpened(getIntent());


    }
}
