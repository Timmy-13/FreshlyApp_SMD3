package com.assignment.freshly.Activities.LandingPageVendor;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.assignment.freshly.R;
import com.assignment.freshly.AsyncTask.Product.DeleteProduct;
import com.assignment.freshly.AsyncTask.Product.GetProduct;
import com.assignment.freshly.AsyncTask.Product.UpdateProduct;
import com.assignment.freshly.Entity.Product;

public class ProductDetailActivity extends AppCompatActivity {
    TextView productTitle, productDesc;
    ImageView productImage;
    Button update, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        productTitle = findViewById(R.id.productTitle);
        productDesc = findViewById(R.id.productDescription);
        update = findViewById(R.id.editButton);
        delete= findViewById(R.id.deleteButton);
        productImage = findViewById(R.id.productImage);
        int productId = getIntent().getIntExtra("productId", -1);
        if(productId != -1){
            new GetProduct(this, new GetProduct.GetProductListener() {
                @Override
                public void onGetProductSuccess(Product product) {
                    productTitle.setText(product.getTitle());
                    productDesc.setText(product.getDescription());
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDeleteAlert(product);
                        }
                    });

                    update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showUpdateDialog(product);
                        }
                    });
                }

                @Override
                public void onGetProductFailure() {

                }
            }).execute(productId);
        }

    }

    private void showDeleteAlert(Product product){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete")
                .setMessage("Are you sure you want to delete " + product.getTitle())
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new DeleteProduct(getApplicationContext(), new DeleteProduct.DeleteProductListener() {
                            @Override
                            public void DeleteProductSuccess() {
                                finish();
                            }

                            @Override
                            public void DeleteProductFailure() {
                                finish();
                            }
                        }).execute(product);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private void showUpdateDialog(Product product){
        View dialogView = getLayoutInflater().inflate(R.layout.update_product_alert_dialog, null);

        EditText editTitle = dialogView.findViewById(R.id.productTitle);
        EditText editDescription = dialogView.findViewById(R.id.productDescription);

        editTitle.setText(product.getTitle());
        editDescription.setText(product.getDescription());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setTitle("Update Product")
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String updatedTitle = editTitle.getText().toString();
                        String updatedDesc = editDescription.getText().toString();

                        product.setTitle(updatedTitle);
                        product.setDescription(updatedDesc);

                        new UpdateProduct(getApplicationContext(), new UpdateProduct.UpdateProductListener(){
                            @Override
                            public void onUpdateProductSuccess(){
                                dialog.dismiss();
                                finish();
                            }

                            @Override
                            public void onUpdateProductFailure(){
                                dialog.dismiss();
                                finish();
                            }
                        }).execute(product);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }


}