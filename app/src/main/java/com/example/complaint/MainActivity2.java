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

public class MainActivity2 extends AppCompatActivity {
    Button otp, register;
    EditText phno, enterotp;
    private String mVerificationId;
    private FirebaseAuth mauth;
    private FirebaseUser muser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        otp = findViewById(R.id.otp);
        register = findViewById(R.id.register);
        phno = findViewById(R.id.phno);
        enterotp = findViewById(R.id.enterotp);
        mauth = FirebaseAuth.getInstance();
        muser = mauth.getCurrentUser();
        if (muser != null) {
            Intent cmp = new Intent(MainActivity2.this, complaint.class);
            cmp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            cmp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(cmp);
        }
        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pno = phno.getText().toString();
                verify(pno);
                register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String cd = enterotp.getText().toString();
                        verifywith(cd);
                    }
                });


            }
        });
    }

    private void verify(String no) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(no, 60, TimeUnit.SECONDS, this, mCallbacks);

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            verifywith(code);
            Log.d("", "success");

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();
            Log.d("", "error:" + e);
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
        }
    };

    private void verifywith(String s) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, s);
        signup(credential);
    }

    private void signup(PhoneAuthCredential credential) {
        mauth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent cmp = new Intent(MainActivity2.this, complaint.class);
                            cmp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            cmp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(cmp);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}