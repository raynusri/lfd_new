package com.example.lfd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Homepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        VideoView videoView = findViewById(R.id.videoView3);
        videoView.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.lfd);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

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
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}

