package com.example.expensetrackerproject.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.expensetrackerproject.NavigationActivity;
import com.example.expensetrackerproject.R;

public class LoginActivity extends AppCompatActivity
{

    private EditText inUsername, inPassword;
    private Button btnLogin;
    private Button btnNewUser;

    private LoginController controller;

    private int loginAttempts;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        controller =  new LoginController(this);
        loginAttempts = 0;

        //login button
        inUsername = (EditText)findViewById(R.id.login_username);
        inPassword = (EditText)findViewById(R.id.login_password);
        btnLogin = (Button)findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = inUsername.getText().toString();
                String pass = inPassword.getText().toString();
                if(controller.login(user, pass)) {
                    Intent navActivity = new Intent(getApplicationContext(), NavigationActivity.class);
                    navActivity.putExtra("USERNAME",  user);
                    startActivity(navActivity);

                }
                else {
                    loginAttempts++;
                    Toast.makeText(getApplicationContext(), "Username or password is incorrect", Toast.LENGTH_LONG).show();
                }

                if(loginAttempts == 3)
                    finish();
            }
        });

        //new user button
        btnNewUser = (Button)findViewById(R.id.btn_newUser);
        btnNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newuserActivity = new Intent(getApplicationContext(), NewUserActivity.class);
                startActivity(newuserActivity);
            }
        });
    }
}
