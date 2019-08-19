package com.example.utabazaarsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class chessActivity extends AppCompatActivity {

    DatabaseHelper db;
    LoginActivity username;
    String member;
    Button Join;
    Button Leave;
    TextView number;
    TextView tile;
    TextView des;
    String group_des;
    String group_title;
    int num_mem=0;
    String get_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        db = new DatabaseHelper(this);
        Join = (Button)findViewById(R.id.joinbtn);
        Leave = (Button)findViewById(R.id.leavebtn);
        number = (TextView)findViewById(R.id.numbertxt);
        tile = (TextView)findViewById(R.id.titleText);
        des = (TextView)findViewById(R.id.desText);

        Intent intent = getIntent();
        member=intent.getStringExtra("EXTRA_USER");

        group_title=intent.getStringExtra("EXTRA_GROUP_NAME");
        tile.setText(group_title);

        String get_des = db.get_group_info(group_title);
       // String get_des=cursor.getString(0);
        des.setText(get_des);

        //user joins group
        Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent intent = getIntent();
                //member=intent.getStringExtra("EXTRA_USER");

                if (db.addMember(member)) {
                    Toast.makeText(chessActivity.this, "Member added", Toast.LENGTH_SHORT).show();
                   num_mem++;
                   get_num = Integer.toString(num_mem);
                   number.setText(get_num);
                }
                else {
                    Toast.makeText(chessActivity.this, "Member not added", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //user leaves group
        Leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent intent = getIntent();
               // member=intent.getStringExtra("EXTRA_USER");

                    if(db.deleteUser(member)){
                        Toast.makeText(chessActivity.this, "User have left the group", Toast.LENGTH_SHORT).show();
                        num_mem--;
                        get_num = Integer.toString(num_mem);
                        number.setText(get_num);
                    }
                    else {
                        Toast.makeText(chessActivity.this, "User still in group", Toast.LENGTH_SHORT).show();
                    }
            }
        });


    }
}
