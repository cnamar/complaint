package com.example.complaint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {
    Button complaint,blog,sign,reset;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db;
    String emailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        complaint=findViewById(R.id.complaint);
        blog=findViewById(R.id.blog);
        sign=findViewById(R.id.signout);
        reset=findViewById(R.id.reset);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        db=FirebaseFirestore.getInstance();
        //
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
                Intent main=new Intent(HomeActivity.this,blogsession.class);
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
       reset.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               emailAddress="u cheat";
               Log.d("tag",emailAddress+"      "+"ivadem");
               db.collection("users").document(mAuth.getCurrentUser().getUid()).get()
                       .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                           @Override
                           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                               if (task.isSuccessful())
                               {
                                   DocumentSnapshot doc=task.getResult();
                                   String emailAddress=doc.getString("email");

                                   mAuth.sendPasswordResetEmail(emailAddress)
                                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                                               @Override
                                               public void onComplete(@NonNull Task<Void> task) {
                                                   if (task.isSuccessful()) {
                                                       Toast.makeText(getApplicationContext(),"A password reset link has been sent to your email",Toast.LENGTH_LONG).show();
                                                   }
                                                   else
                                                   {
                                                       Toast.makeText(getApplicationContext(),"failure",Toast.LENGTH_LONG).show();
                                                   }
                                               }
                                           });

                               }
                               else
                               {
                                   Log.d("tag","failed!!");
                               }
                           }
                       });



           }
       });
    }



}