package com.example.complaint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.nfc.Tag;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    Button complaint,blog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        complaint=findViewById(R.id.complaint);
        blog=findViewById(R.id.blog);
        complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main=new Intent(MainActivity.this,MainActivity2.class);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(main);
            }
        });


    }



    }
