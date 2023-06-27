package com.example.geolocalisationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginIntent extends AppCompatActivity {
    private EditText userName;
    private EditText password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        this.userName = findViewById(R.id.username);
        this.password = findViewById(R.id.passwd);

        //String username = this.userName.getText().toString();
        String username = "aiman";
        //String paswd = this.password.getText().toString();
        String paswd = "1234";
        findViewById(R.id.btnlogin).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("*****************************");
                System.out.println(username);
                System.out.println(paswd);
                System.out.println("*****************************");

                if(username.equals("aiman") && paswd.equals("1234") ){
                    Intent main = new Intent(v.getContext(), MainActivity.class);
                    startActivity(main);
                }else{
                    Toast.makeText(LoginIntent.this, "Wrong Login Information", Toast.LENGTH_SHORT).show( );
                }

            }
        });
    }

}
