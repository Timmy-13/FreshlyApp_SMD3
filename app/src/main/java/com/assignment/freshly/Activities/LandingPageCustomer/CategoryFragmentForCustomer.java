package com.assignment.freshly.Activities.LandingPageCustomer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.assignment.freshly.Activities.LandingPageVendor.CategoryFragmentForVendor;
import com.assignment.freshly.Adapter.ListViewAdapterCustomer;
import com.assignment.freshly.AsyncTask.Product.GetProductsByCategory;
import com.assignment.freshly.AsyncTask.Product.GetProductsForCustomer;
import com.assignment.freshly.Entity.Product;
import com.assignment.freshly.R;

import java.util.List;

public class CategoryFragmentForCustomer extends Fragment {
    ListView productList;
    TextView noProducts;
    TextView title;
    private String categoryName;

    public static CategoryFragmentForCustomer newInstance(String categoryName) {
        CategoryFragmentForCustomer fragment = new CategoryFragmentForCustomer();
        Bundle args = new Bundle();
        args.putString("category_name", categoryName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View productView = inflater.inflate(R.layout.fragment_category, container, false);
        productList = productView.findViewById(R.id.product_list_view);
        title = productView.findViewById(R.id.product_category);
        if(categoryName != null){
            title.setText(categoryName.toUpperCase());
        }else{
            title.setText("ALL PRODUCTS");
        }
        noProducts = productView.findViewById(R.id.No_products_found);

        loadProductList();

        return productView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryName = getArguments().getString("category_name");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadProductList();
    }

    private void getCustomerProducts(){
        new GetProductsForCustomer(getContext(), new GetProductsForCustomer.GetProductsForCustomerListener() {
            @Override
            public void onSucess(List<Product> products) {
                ListViewAdapterCustomer listViewAdapterCustomer = new ListViewAdapterCustomer(getContext(), products);
                productList.setAdapter(listViewAdapterCustomer);
                noProducts.setVisibility(View.GONE);
            }

            @Override
            public void onFailure() {
                noProducts.setText("No " + categoryName + "s found !!");
                noProducts.setVisibility(View.VISIBLE);
            }
        }).execute();
    }

    private void getCustomerProductsByCategory(){
       new GetProductsByCategory(getContext(), new GetProductsByCategory.GetProductsByCategoryListener() {
           @Override
           public void onFailure() {
               noProducts.setText("No " + categoryName + "s found !!");
               noProducts.setVisibility(View.VISIBLE);
           }

           @Override
           public void onSuccess(List<Product> products) {
               ListViewAdapterCustomer listViewAdapterCustomer = new ListViewAdapterCustomer(getContext(), products);
               productList.setAdapter(listViewAdapterCustomer);
               noProducts.setVisibility(View.GONE);
           }
       }).execute(categoryName);
    }

    private void loadProductList() {
        if(categoryName == null){
            getCustomerProducts();
        }else{
            getCustomerProductsByCategory();
        }
    }
}