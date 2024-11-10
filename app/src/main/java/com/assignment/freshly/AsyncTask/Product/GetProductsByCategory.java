package com.assignment.freshly.AsyncTask.Product;

import android.content.Context;
import android.os.AsyncTask;

import com.assignment.freshly.Dao.ProductDao;
import com.assignment.freshly.DatabaseConfig.DatabaseClient;
import com.assignment.freshly.Entity.Product;

import java.util.Collections;
import java.util.List;

public class GetProductsByCategory extends AsyncTask<String, Void, List<Product>> {
    private Context context;
    private GetProductsByCategoryListener getProductsByCategoryListener;

    public GetProductsByCategory(Context context, GetProductsByCategoryListener getProductsByCategoryListener){
        this.context = context;
        this.getProductsByCategoryListener = getProductsByCategoryListener;
    }

    @Override
    protected List<Product> doInBackground(String... strings) {
        ProductDao productDao = DatabaseClient.getInstance(context).freshlyDatabase.productDao();
        return productDao.getProductsByCategory(strings[0]);
    }

    @Override
    protected void onPostExecute(List<Product> products){
        if(products == null){
            getProductsByCategoryListener.onFailure();
        }
        else{
            getProductsByCategoryListener.onSuccess(products);
        }
    }

    public interface GetProductsByCategoryListener{
        void onFailure();
        void onSuccess(List<Product> products);
    }
}
