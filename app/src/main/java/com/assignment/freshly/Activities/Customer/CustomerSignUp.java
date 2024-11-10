package com.assignment.freshly.Activities.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.assignment.freshly.R;
import com.assignment.freshly.AsyncTask.Customer.InsertCustomer;
import com.assignment.freshly.Entity.Customer;
import com.assignment.freshly.Utils.ValidationUtils;

public class CustomerSignUp extends AppCompatActivity {

    EditText emailTextView, passwordTextView;
    Button signupButton;
    RadioGroup genderRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign_up);
        emailTextView = findViewById(R.id.signup_email);
        passwordTextView = findViewById(R.id.signup_password);
        signupButton = findViewById(R.id.signup_button);
        genderRadioGroup = findViewById(R.id.gender_radio_group);

        signupButton.setOnClickListener(v -> {
            String email = emailTextView.getText().toString().trim();
            String password = passwordTextView.getText().toString().trim();
            int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();

            if (ValidationUtils.isEmailValid(email) && ValidationUtils.isPasswordValid(password) && ValidationUtils.isGenderValid(selectedGenderId)) {
                RadioButton selectedGenderButton = findViewById(selectedGenderId);
                String gender = selectedGenderButton.getText().toString();
                System.out.println("Gender " + gender);
                Customer customerToAdd = new Customer(email, password, gender);
                new InsertCustomer(getApplicationContext(), new InsertCustomer.CustomerSignupListener() {
                    @Override
                    public void onSignUpSuccess() {
                        Intent intent = new Intent(CustomerSignUp.this, CustomerLogin.class);
                        startActivity(intent);
                        finish();
                    }
                    @Override
                    public void onSignUpFailure() {
                        Toast.makeText(getApplicationContext(), "Email Already Exists", Toast.LENGTH_SHORT).show();
                    }
                }).execute(customerToAdd);

            } else {
                Toast.makeText(this, "Provide all necessary details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goToCustomerLogin(View view) {
        Intent intent = new Intent(this, CustomerLogin.class);
        startActivity(intent);
        finish();
    }

}
