package com.example.utabazaarsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class groupListPage extends AppCompatActivity {

    DatabaseHelper db;
    ListView listView;
    Button create;
    EditText addNames;
    EditText addDes;

    ArrayList<String> list_groups;
    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_list);


        db = new DatabaseHelper(this);
        list_groups = new ArrayList<>();

        create = findViewById(R.id.createbtn);
        addNames = findViewById(R.id.add_name);
        addDes = findViewById(R.id.add_des);
        listView = findViewById(R.id.grouplist);


       /* String Des = addDes.getText().toString();
        Intent chessClubIntent2 = new Intent(groupListPage.this,chessActivity.class);
        chessClubIntent2.putExtra("EXTRA_GROUP_DES",Des);*/

        viewData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String text = listView.getItemAtPosition(i).toString().trim();
                //String Des =  listView.getItemAtPosition(i+1).toString().trim();

                Intent chessClubIntent = new Intent(groupListPage.this,chessActivity.class);
                //Intent chessClubIntent2 = new Intent(groupListPage.this,chessActivity.class);
                chessClubIntent.putExtra("EXTRA_GROUP_NAME",text);
               // chessClubIntent.putExtra("EXTRA_GROUP_DES",Des);
                //Log.d("Debug",Des);
                //Log.d("Debug2",text);
                startActivity(chessClubIntent);
                Toast.makeText(groupListPage.this, ""+text, Toast.LENGTH_SHORT).show();
            }
        });

        //creates group
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = addNames.getText().toString();
                String Des = addDes.getText().toString();


                if (!name.equals("") && !Des.equals("") && db.addGroup(name, Des)) {
                    Toast.makeText(groupListPage.this, "Group added", Toast.LENGTH_SHORT).show();
                    addNames.setText("");
                    addDes.setText("");
                    list_groups.clear();
                   viewData();
                }
                else {
                    Toast.makeText(groupListPage.this, "Group not added", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    //shows all cgroups in database
    private void viewData() {
        Cursor cursor = db.viewData();

        if(cursor.getCount() == 0){
            Toast.makeText(groupListPage.this, "There are no groups to add", Toast.LENGTH_SHORT).show();
        }
        else{
            while(cursor.moveToNext()){
                list_groups.add(cursor.getString(1));
            }

            adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,android.R.id.text1,list_groups);
            listView.setAdapter(adapter);
        }

    }



}
