package com.assignment.freshly.Activities.Vendor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.assignment.freshly.AsyncTask.Vendor.InsertVendor;
import com.assignment.freshly.Entity.Vendor;
import com.assignment.freshly.R;
import com.assignment.freshly.Utils.ValidationUtils;

public class VendorSignUp extends AppCompatActivity {
    EditText usernameTextView, passwordTextView, phoneTextView, addressTextView;
    Button signUpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("In Vendor Signup");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_sign_up);
        usernameTextView = findViewById(R.id.signup_username);
        passwordTextView = findViewById(R.id.signup_password);
        phoneTextView = findViewById(R.id.signup_phone);
        addressTextView = findViewById(R.id.signup_address);
        signUpBtn = findViewById(R.id.signup_button);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameTextView.getText().toString();
                String password = passwordTextView.getText().toString();
                String phone = phoneTextView.getText().toString();
                String address = addressTextView.getText().toString();

                if(ValidationUtils.isUsernameValid(username) && ValidationUtils.isPasswordValid(password) && ValidationUtils.isValidPhoneNumber(phone) && ValidationUtils.isAddressValid(address)){
                    new InsertVendor(getApplicationContext(), new InsertVendor.InsertVendorListener() {
                        @Override
                        public void insertListenerSuccess() {
                            System.out.println("Vendor signed up");
                            Intent intent = new Intent(VendorSignUp.this, VendorLogin.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void insertListenerFailure() {
                            Toast.makeText(getApplicationContext(), "Username Already Exists", Toast.LENGTH_SHORT).show();
                        }
                    }).execute(new Vendor(username, password, phone, address));
                }
                else {
                    Toast.makeText(getApplicationContext(), "Provide all neccessary details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void goToVendorLogin(View view){
        Intent intent = new Intent(this, VendorLogin.class);
        startActivity(intent);
    }
}