package com.example.complaint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    Button complaint,blog,sign;
    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        complaint=findViewById(R.id.complaint);
        blog=findViewById(R.id.blog);
        sign=findViewById(R.id.signout);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main=new Intent(HomeActivity.this,complaint.class);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(main);
            }
        });
        blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main=new Intent(HomeActivity.this,blog.class);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(main);
            }
        });
       sign.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mAuth.signOut();
               Intent main=new Intent(HomeActivity.this,MainActivity.class);
               main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
               startActivity(main);
           }
       });

    }



}