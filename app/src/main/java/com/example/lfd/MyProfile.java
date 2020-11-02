package com.example.lfd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MyProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    TextView fullname, email, dob, phone;
FirebaseAuth fAuth;
FirebaseFirestore fStore;
String userId;
ImageView profileImage;
Button changeProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
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
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                startActivity(new Intent(getApplicationContext(), Homepage.class));
                break;
            case R.id.nav_menu:
                startActivity(new Intent(getApplicationContext(), Menu.class));
                break;
            case R.id.nav_outlet:
                startActivity(new Intent(getApplicationContext(), Outlet.class));
                break;
            case R.id.nav_cater:
                startActivity(new Intent(getApplicationContext(), Catering.class));
                break;
            case R.id.nav_help:
                startActivity(new Intent(getApplicationContext(), HelpDesk.class));
                break;
            case R.id.nav_profile:
                startActivity(new Intent(getApplicationContext(), MyProfile.class));
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "Logged Out.", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}