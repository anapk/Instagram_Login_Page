package com.example.cody.insagram_clone;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;

import java.util.ArrayList;
import java.util.List;

public class UserList extends Activity {
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> usernames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);
        final ListView userList = (ListView) findViewById(R.id.userlist);
        usernames = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, usernames);

        AVQuery<AVUser> query = AVUser.getQuery();
        query.whereNotEqualTo("username", AVUser.getCurrentUser().getUsername());
        query.addAscendingOrder("username");

        query.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        //将用户列表存到arraylist中
                        for (AVUser user : list) {
                            Log.i("username", user.getUsername().toString());
                            usernames.add(user.getUsername());
                        }
                        userList.setAdapter(arrayAdapter);
                    }
                } else if (e != null) {
                    Log.i("failed", e.getMessage());
                }

            }
        });

    }
}
