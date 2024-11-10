package com.assignment.freshly.Activities.Vendor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.assignment.freshly.Activities.LandingPageVendor.LandingPageForVendor;
import com.assignment.freshly.AsyncTask.Vendor.GetVendorByUsernameAndPassword;
import com.assignment.freshly.R;

public class VendorLogin extends AppCompatActivity {

    EditText loginUsername, loginPassword;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("In Vendor Login");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_login);
        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = loginUsername.getText().toString();
                String password = loginPassword.getText().toString();

                if(!username.isEmpty() && !password.isEmpty()){
                    new GetVendorByUsernameAndPassword(getApplicationContext(), new GetVendorByUsernameAndPassword.GetCustomerListener() {
                        @Override
                        public void GetCustomerListenerSuccess(int vendorId) {
                            SharedPreferences sharedPreferences = getSharedPreferences("Vendor_Details", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("Vendor_Id", vendorId);
                            editor.apply();

                            Intent intent = new Intent(VendorLogin.this, LandingPageForVendor.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void GetCustomerListenerFailure() {
                            Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                        }
                    }).execute(username, password);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Provide Credentials to Login", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void goToVendorSignup(View view){
        Intent intent = new Intent(this, VendorSignUp.class);
        startActivity(intent);
    }
}