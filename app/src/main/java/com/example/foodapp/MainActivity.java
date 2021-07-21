package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button login;
    Button register;
    TextView skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        login=(Button)findViewById(R.id.btnlogin);
        register=(Button)findViewById(R.id.btnRegister);
        skip=(TextView)findViewById(R.id.skiptxt);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( MainActivity.this,"Login has been clicked" ,Toast.LENGTH_LONG).show();
                Intent intent =new Intent(MainActivity.this,SignInActivity.class);
                startActivity(intent);

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( MainActivity.this,"register has been clicked" ,Toast.LENGTH_LONG).show();
                Intent intent =new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);

            }
        });

    }
}