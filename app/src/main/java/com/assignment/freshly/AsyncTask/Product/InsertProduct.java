package com.assignment.freshly.AsyncTask.Product;

import android.content.Context;
import android.os.AsyncTask;

import com.assignment.freshly.DatabaseConfig.DatabaseClient;
import com.assignment.freshly.Dao.ProductDao;
import com.assignment.freshly.Entity.Product;

public class InsertProduct extends AsyncTask<Product, Void, Long> {
    private Context context;
    private InsertProductListener insertProductListener;

    public InsertProduct(Context context, InsertProductListener insertProductListener){
        this.context = context;
        this.insertProductListener = insertProductListener;
    }


    @Override
    protected Long doInBackground(Product... products) {
        ProductDao productDao = DatabaseClient.getInstance(context).freshlyDatabase.productDao();
        return productDao.insert(products[0]);
    }

    @Override
    protected void onPostExecute(Long result){
        if(result != -1){
            insertProductListener.onSuccess();
        }else{
            insertProductListener.onFailure();
        }
    }

    public interface InsertProductListener{
        void onSuccess();
        void onFailure();
    }
}
