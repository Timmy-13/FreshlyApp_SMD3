package com.assignment.freshly;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.assignment.freshly.Activities.Customer.CustomerSignUp;
import com.assignment.freshly.Activities.Vendor.VendorSignUp;
import com.assignment.freshly.AsyncTask.Category.InsertCategory;
import com.assignment.freshly.AsyncTask.Product.InsertProduct;
import com.assignment.freshly.AsyncTask.Vendor.InsertVendor;
import com.assignment.freshly.Entity.Category;
import com.assignment.freshly.Entity.Product;
import com.assignment.freshly.Entity.Vendor;

public class MainActivity extends AppCompatActivity {

    Button asVendor, asCustomer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initProject(savedInstanceState);
        asVendor = findViewById(R.id.btnLoginAsVendor);
        asCustomer = findViewById(R.id.btnLoginAsCustomer);

        asVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VendorSignUp.class);
                startActivity(intent);
//                finish();
            }
        });

        asCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustomerSignUp.class);
                startActivity(intent);
//                finish();
            }
        });
    }

    private void initProject(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences("FRESHLY_PREFERENCES", MODE_PRIVATE);
        boolean isInitialized = sharedPreferences.getBoolean("isInitialized", false);

        if (!isInitialized) {
            new InsertCategory(this).execute(new Category("vegetable"));
            new InsertCategory(this).execute(new Category("fruit"));
            new InsertCategory(this).execute(new Category("dry fruit"));

            sharedPreferences.edit().putBoolean("isInitialized", true).apply();
        }
    }
}