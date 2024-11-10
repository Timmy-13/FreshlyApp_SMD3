package com.assignment.freshly.Activities.LandingPageVendor;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment.freshly.Adapter.ListViewAdapterVendor;
import com.assignment.freshly.AsyncTask.Category.GetCategory;
import com.assignment.freshly.AsyncTask.Product.GetVendorProducts;
import com.assignment.freshly.AsyncTask.Product.InsertProduct;
import com.assignment.freshly.R;
import com.assignment.freshly.AsyncTask.Product.GetProductByCateogoryAndVendor;
import com.assignment.freshly.Entity.Product;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class CategoryFragmentForVendor extends Fragment {

    ListView productList;
    TextView noProducts;
    TextView title;
    Button addProductBtn;
    private String categoryName;
    private int vendorId;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Bitmap selectedImageBitmap;
    private byte[] productImageBytes;
    private ImageView productImageView;

    public static CategoryFragmentForVendor newInstance(String categoryName) {
        CategoryFragmentForVendor fragment = new CategoryFragmentForVendor();
        Bundle args = new Bundle();
        args.putString("category_name", categoryName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Vendor_Details", Context.MODE_PRIVATE);
        vendorId = sharedPreferences.getInt("Vendor_Id",-1);
        if (getArguments() != null) {
            categoryName = getArguments().getString("category_name");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View productView = inflater.inflate(R.layout.fragment_vegetables, container, false);
        productList = productView.findViewById(R.id.product_list_view);
        title = productView.findViewById(R.id.product_category);
        addProductBtn = productView.findViewById(R.id.addProductBtn);
        productImageView = productView.findViewById(R.id.product_image_view);
        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddProductDialog();
            }
        });
        if(categoryName != null){
            title.setText(categoryName.toUpperCase());
            addProductBtn.setVisibility(View.VISIBLE);
        }else{
            title.setText("ALL PRODUCTS");
            addProductBtn.setVisibility(View.GONE);
        }
        noProducts = productView.findViewById(R.id.No_products_found);

        loadProductList();

        return productView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadProductList();
    }

    private void getVendorProducts(){
        new GetVendorProducts(getContext(), new GetVendorProducts.GetVendorProductsListener() {
            @Override
            public void onGetVendorProductsSuccess(List<Product> products) {
                ListViewAdapterVendor listViewAdapter = new ListViewAdapterVendor(getContext(), products);
                productList.setAdapter(listViewAdapter);
                noProducts.setVisibility(View.GONE);
            }

            @Override
            public void onGetVendorProductsFailure() {
                noProducts.setText("No " + categoryName + "s found !!");
                noProducts.setVisibility(View.VISIBLE);
            }
        }).execute(vendorId);
    }

    private void getProductsByVendorAndCategory(){
        new GetProductByCateogoryAndVendor(getContext(), new GetProductByCateogoryAndVendor.returnCategoryProduct() {
            @Override
            public void onSuccess(List<Product> products) {
                if (products != null && !products.isEmpty()) {
                    ListViewAdapterVendor listViewAdapter = new ListViewAdapterVendor(getContext(), products);
                    productList.setAdapter(listViewAdapter);
                    noProducts.setVisibility(View.GONE);
                } else {
                    noProducts.setText("No " + categoryName + "s found !!");
                    noProducts.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure() {
                noProducts.setText("Failed to load " + categoryName + "s.");
            }
        }).execute(vendorId, categoryName);
    }

    private void loadProductList() {
        if(categoryName == null){
            getVendorProducts();
        }else{
            getProductsByVendorAndCategory();
        }
    }

    private void showAddProductDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_product_dialog, null);

        EditText productTitleEditText = dialogView.findViewById(R.id.product_title);
        EditText productDescriptionEditText = dialogView.findViewById(R.id.product_description);
        Button saveProductButton = dialogView.findViewById(R.id.save_product_button);
        ImageView dialogProductImageView = dialogView.findViewById(R.id.product_image_view);
        Button selectImageButton = dialogView.findViewById(R.id.select_image_button);
        productImageView = dialogProductImageView;

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        saveProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = productTitleEditText.getText().toString().trim();
                String description = productDescriptionEditText.getText().toString().trim();

                if (title.isEmpty() || description.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill in both fields", Toast.LENGTH_SHORT).show();
                } else {
                    new GetCategory(getContext(), new GetCategory.GetCategoryListener() {
                        @Override
                        public void onGetCategorySuccess(int categoryId) {
                            System.out.println("Get Category Success");

                            new InsertProduct(getContext(), new InsertProduct.InsertProductListener() {
                                @Override
                                public void onSuccess() {
                                    loadProductList();
                                    System.out.println("Insert Product SUccess");
                                    dialog.dismiss();
                                }

                                @Override
                                public void onFailure() {
                                    System.out.println("Insert Product Failue");
                                    dialog.dismiss();
                                }
                            }).execute(new Product(title, description,categoryId, vendorId, productImageBytes));
                        }

                        @Override
                        public void onGetCategoryFailure() {

                        }
                    }).execute(categoryName);
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            try {
                selectedImageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                productImageBytes = getBytesFromBitmap(selectedImageBitmap);
                productImageView.setImageBitmap(selectedImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

}
