package com.example.complaint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private Button registerBtn;
    private EditText emailField,name, passwordField,ph;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseFirestore db;
    private TextView loginTxtView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loginTxtView = (TextView)findViewById(R.id.loginTxtView);
        registerBtn = (Button)findViewById(R.id.registerBtn);
        emailField = (EditText)findViewById(R.id.emailField);
        name=(EditText)findViewById(R.id.name);
        passwordField = (EditText)findViewById(R.id.passwordField);
        ph=(EditText)findViewById(R.id.editTextPhone);
        db=(FirebaseFirestore)FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        loginTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RegisterActivity.this, "LOADING...", Toast.LENGTH_LONG).show();
                final String uname=name.getText().toString();
                final String email = emailField.getText().toString().trim();
                final String password = passwordField.getText().toString().trim();
                final String phno=ph.getText().toString().trim();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(uname)&&!TextUtils.isEmpty(password)){
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        String user_id = mAuth.getCurrentUser().getUid();
                                       Map<String, Object> docData = new HashMap<>();
                                      docData.put("Name",uname);
                                        docData.put("email",email);
                                       docData.put("phone_no",phno);
                                        DatabaseReference current_user_db = mDatabase.child(user_id);
                                        current_user_db.child("Username").setValue(uname);
                                        current_user_db.child("Image").setValue("Default");
                                       db.collection("users").document(user_id).set(docData);
                                        Toast.makeText(RegisterActivity.this, "Registeration Succesful.A verification mail has been sent to your account.Verify your email.", Toast.LENGTH_SHORT).show();
                                        Intent page=new Intent(RegisterActivity.this, MainActivity.class);
                                        page.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        page.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(page);
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        }
                    });
                }else {

                    Toast.makeText(RegisterActivity.this, "Complete all fields", Toast.LENGTH_SHORT).show();
                }            }        });
    }
}