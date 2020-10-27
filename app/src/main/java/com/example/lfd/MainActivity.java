package com.example.lfd;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {
TextView fullname, email, dob, phone;
FirebaseAuth fAuth;
FirebaseFirestore fStore;
String userId;
ImageView profileImage;
Button changeProfileImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fullname=findViewById(R.id.profileName);
        email=findViewById(R.id.profileEmail);
        dob=findViewById(R.id.newDOB);
        phone=findViewById(R.id.profilePhone);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        userId=fAuth.getCurrentUser().getUid();
        profileImage=findViewById(R.id.profileImage);
changeProfileImage=findViewById(R.id.changeProfile);


        DocumentReference documentReference=fStore.collection("users").document(userId);
documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
    @Override
    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
phone.setText(documentSnapshot.getString("phone"));
fullname.setText(documentSnapshot.getString("fName"));
email.setText(documentSnapshot.getString("email"));
dob.setText(documentSnapshot.getString("dob"));


    }
});

        changeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Intent i=new Intent(v.getContext(),EditProfile.class);
i.putExtra("fullname",fullname.getText().toString());
i.putExtra("email",email.getText().toString());
i.putExtra("phone",phone.getText().toString());
 i.putExtra("dob",dob.getText().toString());
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