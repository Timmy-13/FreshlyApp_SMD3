package com.assignment.freshly.AsyncTask.Product;

import android.content.Context;
import android.os.AsyncTask;

import com.assignment.freshly.Dao.ProductDao;
import com.assignment.freshly.DatabaseConfig.DatabaseClient;
import com.assignment.freshly.Entity.Product;

import java.util.List;

public class GetProductsForCustomer extends AsyncTask<Void, Void, List<Product>> {
    private Context context;
    private GetProductsForCustomerListener getProductsForCustomerListener;

    public GetProductsForCustomer(Context context, GetProductsForCustomerListener getProductsForCustomerListener){
        this.context = context;
        this.getProductsForCustomerListener = getProductsForCustomerListener;
    }

    @Override
    protected List<Product> doInBackground(Void... voids) {
        ProductDao productDao = DatabaseClient.getInstance(context).freshlyDatabase.productDao();
        return productDao.getAllProducts();

    }

    @Override
    protected void onPostExecute(List<Product> products){
        if(products == null){
            getProductsForCustomerListener.onFailure();
        }
        else{
            getProductsForCustomerListener.onSucess(products);
        }
    }

    public interface GetProductsForCustomerListener{
        void onSucess(List<Product> products);
        void onFailure();
    }
}
