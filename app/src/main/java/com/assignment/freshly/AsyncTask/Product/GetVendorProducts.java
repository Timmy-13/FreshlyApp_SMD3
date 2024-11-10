package com.assignment.freshly.AsyncTask.Product;

import android.content.Context;
import android.os.AsyncTask;

import com.assignment.freshly.Dao.ProductDao;
import com.assignment.freshly.DatabaseConfig.DatabaseClient;
import com.assignment.freshly.Entity.Product;

import java.sql.DataTruncation;
import java.util.Collections;
import java.util.List;

public class GetVendorProducts extends AsyncTask<Integer, Void, List<Product>> {
    private Context context;
    private GetVendorProductsListener getVendorProductsListener;

    public GetVendorProducts(Context context, GetVendorProductsListener getVendorProductsListener){
        this.context = context;
        this.getVendorProductsListener = getVendorProductsListener;
    }

    @Override
    protected List<Product> doInBackground(Integer... integers) {
        ProductDao productDao = DatabaseClient.getInstance(context).freshlyDatabase.productDao();
        return productDao.getAllVendorProducts(integers[0]);
    }

    @Override
    protected void onPostExecute(List<Product> products){
        if(products == null){
            getVendorProductsListener.onGetVendorProductsFailure();
        }
        else {
            getVendorProductsListener.onGetVendorProductsSuccess(products);
        }
    }

    public interface GetVendorProductsListener{
        void onGetVendorProductsSuccess(List<Product> products);
        void onGetVendorProductsFailure();
    }
}
