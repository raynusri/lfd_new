package com.example.lfd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button mLoginBtn;
    TextView mCreateBtn,forgotTextLink;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmail=findViewById(R.id.Email);
        mPassword=findViewById(R.id.Password);
        progressBar=findViewById(R.id.progressBar);
        fAuth=FirebaseAuth.getInstance();
        mLoginBtn=findViewById(R.id.LoginBtn);
        mCreateBtn=findViewById(R.id.CreateText);
        forgotTextLink=findViewById(R.id.forgotPassword);

        mLoginBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required");
                    return;
                }
                if(password.length()<6){
                    mPassword.setError("Password must be more than 6 characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                //authenticate the user
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){
                       Toast.makeText(Login.this,"Logged in Successfully",Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(getApplicationContext(), Homepage.class));
                   }else{
                       Toast.makeText(Login.this,"Error!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                       progressBar.setVisibility(View.GONE);

                   }

                    }
                });
            }
        });
        mCreateBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));

            }
        });
        forgotTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ResetPassword.class));



//    @Override
//    public void onClick(View v) {
//
//        final EditText resetMail=new EditText(v.getContext());
//        final AlertDialog.Builder passwordResetDialog=new AlertDialog.Builder(v.getContext());
//    passwordResetDialog.setTitle("Reset Password ?");
//    passwordResetDialog.setMessage("Enter your email to Receive Reset Link");
//passwordResetDialog.setView(resetMail);
//
//
//passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//    @Override
//    public void onClick(DialogInterface dialog, int which) {
////extract the Email and send reset Link
//String mail= resetMail.getText().toString();
//fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
//    @Override
//    public void onSuccess(Void aVoid) {
//        Toast.makeText(Login.this,"Reset Link Sent To Your Email.",Toast.LENGTH_SHORT).show();
//
//    }
//}).addOnFailureListener(new OnFailureListener() {
//    @Override
//    public void onFailure(@NonNull Exception e) {
//        Toast.makeText(Login.this,"Error.Reset Link is NOT sent."+e.getMessage(),Toast.LENGTH_SHORT).show();
//    }
//});
//
//    }
//});
//passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
//    @Override
//    public void onClick(DialogInterface dialog, int which) {
//
//    }
//});
//passwordResetDialog.create().show();
//    }

            }
        });

    }


}

