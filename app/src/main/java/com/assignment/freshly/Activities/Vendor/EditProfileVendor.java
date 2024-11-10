package com.assignment.freshly.Activities.Vendor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.assignment.freshly.AsyncTask.Vendor.GetVendorById;
import com.assignment.freshly.Entity.Vendor;
import com.assignment.freshly.R;
import com.assignment.freshly.Utils.ImageUtils;
import com.assignment.freshly.Utils.ValidationUtils;

public class EditProfileVendor extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    int vendorId;
    EditText passwordEdit, phoneEdit, addressEdit;
    Button updateBtn;
    TextView usernameView;
    ImageView profileImage;
    Bitmap selectedImageBitmap;
    Vendor vendorMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        System.out.println("In edit profile");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Intent intent = getIntent();
        vendorId = intent.getIntExtra("VendorId", -1);
        passwordEdit = findViewById(R.id.password_edit);
        phoneEdit = findViewById(R.id.phone_edit);
        addressEdit = findViewById(R.id.address_edit);
        usernameView = findViewById(R.id.username_text);
        updateBtn = findViewById(R.id.update_btn);
        profileImage = findViewById(R.id.profile_image);

        profileImage.setOnClickListener(v -> openGallery());

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordEdit.getText().toString();
                String phone = phoneEdit.getText().toString();
                String address = addressEdit.getText().toString();

                vendorMain.setPassword(password);
                vendorMain.setPhone(phone);
                vendorMain.setAddress(address);

                updateVendorProfile(vendorMain);
            }
        });

        System.out.println("Setup done");

        getVendorById();

        System.out.println("waiting");


    }

    private void getVendorById(){
        new GetVendorById(getApplicationContext(), new GetVendorById.GetVendorByIdListener() {
            @Override
            public void onSuccess(Vendor vendor) {
                addValuesToFields(vendor);
                vendorMain = vendor;
            }

            @Override
            public void onFailure() {

            }
        }).execute(vendorId);
    }

    private void addValuesToFields(Vendor vendor){
        usernameView.setText(vendor.getUsername());
        passwordEdit.setText(vendor.getPassword());
        phoneEdit.setText(vendor.getPhone());
        addressEdit.setText(vendor.getAddress());
        if(vendor.getImagePath() != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(vendor.getImagePath() , 0, vendor.getImagePath().length);
            profileImage.setImageBitmap(bitmap);
        }
        else{
            profileImage.setImageResource(R.drawable.ic_default_image);
        }
    }

    private void updateVendorProfile(Vendor vendor){
        if(ValidationUtils.isPasswordValid(vendor.getPassword()) && ValidationUtils.isValidPhoneNumber(vendor.getPhone()) && ValidationUtils.isAddressValid(vendor.getAddress())){
            if (selectedImageBitmap != null) {
                System.out.println("Image not null");
                byte[] imageBytes = ImageUtils.getImageBytes(selectedImageBitmap);
                vendor.setImagePath(imageBytes);
            }
            new UpdateVendor(getApplicationContext(), new UpdateVendor.UpdateVendorListener() {
                @Override
                public void onSuccess() {
                    finish();
                }

                @Override
                public void onFailure() {
                    Toast.makeText(getApplicationContext(), "Profile Updation failed", Toast.LENGTH_SHORT).show();
                }
            }).execute(vendor);
        }
        else{
            Toast.makeText(getApplicationContext(), "Provide all necessary credentials and in required format", Toast.LENGTH_SHORT).show();
        }

    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                selectedImageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profileImage.setImageBitmap(selectedImageBitmap); // Set the selected image to ImageView
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }
}