package com.assignment.freshly.Activities.LandingPageVendor;

import com.assignment.freshly.Activities.Vendor.EditProfileVendor;
import com.assignment.freshly.AsyncTask.Vendor.GetProfileImage;
import com.assignment.freshly.R; // Make sure this is the correct one

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

public class LandingPageForVendor extends AppCompatActivity {
    ImageView profileImage;
    int vendorId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("In vendor Landing page");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        SharedPreferences sharedPreferences = getSharedPreferences("Vendor_Details", Context.MODE_PRIVATE);
        vendorId = sharedPreferences.getInt("Vendor_Id",-1);

        profileImage = findViewById(R.id.profile_image);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfileVendor.class);
                intent.putExtra("VendorId", vendorId);
                startActivity(intent);
            }
        });
        displayProfileImage();

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);

        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_category_0);

            Fragment defaultFragment = CategoryFragmentForVendor.newInstance(null);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, defaultFragment)
                    .commit();
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int id = item.getItemId();
                if(id == R.id.nav_category_0){
                    selectedFragment = CategoryFragmentForVendor.newInstance(null);
                }else if (id == R.id.nav_category_1) {
                    selectedFragment = CategoryFragmentForVendor.newInstance("vegetable");
                } else if (id == R.id.nav_category_2) {
                    selectedFragment = CategoryFragmentForVendor.newInstance("fruit");
                } else if (id == R.id.nav_category_3) {
                    selectedFragment = CategoryFragmentForVendor.newInstance("dry fruit");
                }
                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, selectedFragment)
                            .commit();
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });



    }

    @Override
    protected void onResume(){
        super.onResume();
        displayProfileImage();
    }

    private void displayProfileImage(){
        new GetProfileImage(getApplicationContext(), new GetProfileImage.GetProfileImageListener() {
            @Override
            public void onSuccess(byte[] res) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(res , 0, res.length);
                profileImage.setImageBitmap(bitmap);
            }

            @Override
            public void onFailure() {
                profileImage.setImageResource(R.drawable.ic_default_image);
            }
        }).execute(vendorId);
    }
}