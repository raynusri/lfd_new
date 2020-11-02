package com.example.lfd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {
public static final String TAG="TAG";
EditText profileFullName, profileEmail, profilePhone,profileDob;
Button saveBtn, back;
FirebaseAuth fAuth;
FirebaseFirestore fStore;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent data =getIntent();
        String fullName=data.getStringExtra("fullname");
        String email=data.getStringExtra("email");
        String phone=data.getStringExtra("phone");
        String dob=data.getStringExtra("dob");
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
user=fAuth.getCurrentUser();
        profileFullName=findViewById(R.id.profileFullName);
        profileEmail=findViewById(R.id.profileEmailAddress);
        profilePhone=findViewById(R.id.profilePhoneNumber);
        profileDob=findViewById(R.id.profileDOB);
back=findViewById(R.id.button_back);
saveBtn=findViewById(R.id.buttonSave);


saveBtn.setOnClickListener(new View.OnClickListener(){
public void onClick(View V){
    if(profileFullName.getText().toString().isEmpty()||profileEmail.getText().toString().isEmpty()||profilePhone.getText().toString().isEmpty()||profileDob.getText().toString().isEmpty()){
        Toast.makeText(EditProfile.this,"One or Many fields are empty",Toast.LENGTH_SHORT).show();
        return;

    }


    final String email=profileEmail.getText().toString();
    user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
            DocumentReference docRef=fStore.collection("users").document(user.getUid());
            Map<String,Object> edited=new HashMap<>();
            edited.put("email",email);
            edited.put("fName",profileFullName.getText().toString());
            edited.put("phone",profilePhone.getText().toString());
            edited.put("dob",profileDob.getText().toString());
            docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(EditProfile.this,"Profile Updated",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MyProfile.class));
                    finish();
                }
            });

        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(EditProfile.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    });

}
}) ;
        profilePhone.setText(phone);
        profileEmail.setText(email);
        profileDob.setText(dob);
        profileFullName.setText(fullName);
Log.d(TAG,"onCreate:" +fullName+" "+email+" "+phone+" "+dob);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(), MyProfile.class);
                startActivity(i);
            }
        });


    }
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }

}