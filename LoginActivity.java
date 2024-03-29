package com.example.utabazaarsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    TextView mTextViewRegister;
    EditText mTextPassword;
    EditText mTextUsername;
    Button mButtonLogin;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);
        mTextUsername = (EditText)findViewById(R.id.usertext);
        mTextPassword = (EditText)findViewById(R.id.passwordtext);
        mButtonLogin = (Button)findViewById(R.id.loginbtn);
        mTextViewRegister = (TextView)findViewById(R.id.registertext);

        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(registerIntent);

            }
        });

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                Boolean res = db.checkUser(user,pwd);
                Intent groupIntent = new Intent(LoginActivity.this, groupListPage.class);

                Intent chessClubIntent = new Intent(LoginActivity.this,chessActivity.class);
                chessClubIntent.putExtra("EXTRA_USER",user);

                if(res == true) {

                    startActivity(groupIntent);
                    Toast.makeText(LoginActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LoginActivity.this," Login Error ",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
