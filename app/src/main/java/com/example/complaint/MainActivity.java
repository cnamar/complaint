///
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
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.google.firebase.firestore.CollectionReference;
        import com.google.firebase.firestore.FirebaseFirestore;
        import com.google.firebase.firestore.Query;
        import com.google.firebase.firestore.QueryDocumentSnapshot;
        import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    private EditText loginEmail, loginPass;
    private TextView signup,forgot;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;
    private Button loginBtn;
    private FirebaseFirestore db;
    int f=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginBtn = (Button)findViewById(R.id.loginBtn);
        loginEmail = (EditText)findViewById(R.id.login_email);
        loginPass = (EditText)findViewById(R.id.login_password);
        forgot=(TextView) findViewById(R.id.forgot);
        signup=(TextView) findViewById(R.id.signUpTxtView);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUser=(FirebaseUser)mAuth.getCurrentUser();
        db=FirebaseFirestore.getInstance();
        if(mUser!=null)
        {
            Intent home=new Intent(MainActivity.this,HomeActivity.class);
            home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(home);
        }
       signup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent sign=new Intent(MainActivity.this,RegisterActivity.class);
               sign.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
               sign.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(sign);
           }
       });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "PROCESSING....", Toast.LENGTH_LONG).show();
                String email = loginEmail.getText().toString().trim();
                String password = loginPass.getText().toString().trim();

                if (!TextUtils.isEmpty(email)&& !TextUtils.isEmpty(password)){

                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                final String id=mAuth.getCurrentUser().getUid();
                                CollectionReference doc=db.collection("users");
                                doc.get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if(task.isSuccessful())
                                                {
                                                    f=0;
                                                    for(QueryDocumentSnapshot document: task.getResult())
                                                    {
                                                        String docid=document.getId();
                                                        if(id.equals(docid))
                                                        {
                                                            if(mAuth.getCurrentUser().isEmailVerified())
                                                            {
                                                                checkUserExistence();
                                                            }
                                                            else
                                                            {
                                                                Toast.makeText(getApplicationContext(),"Verify email",Toast.LENGTH_LONG).show();
                                                            }
                                                            f=1;
                                                        }
                                                    }
                                                    if(f==0)
                                                    {
                                                        Toast.makeText(getApplicationContext(),"Couldn't login,user not found",Toast.LENGTH_LONG).show();
                                                    }

                                                }
                                            }
                                        });


                            }else {
                                Toast.makeText(MainActivity.this, "Couldn't login, User not found", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else {

                    Toast.makeText(MainActivity.this, "Complete all fields", Toast.LENGTH_LONG).show();
                }
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=loginEmail.getText().toString();
                if(email.equals(null))
                {
                    Toast.makeText(getApplicationContext(),"email field is empty",Toast.LENGTH_LONG).show();
                }
                {
                    mAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "A password reset link has been sent to your email id", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "not a valid mail id", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

    }
    public void checkUserExistence(){

        final String user_id = mAuth.getCurrentUser().getUid();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(user_id)){
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                }else {
                    Toast.makeText(MainActivity.this, "User not registered!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}