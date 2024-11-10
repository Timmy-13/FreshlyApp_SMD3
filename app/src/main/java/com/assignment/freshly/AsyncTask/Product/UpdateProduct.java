package com.assignment.freshly.AsyncTask.Product;

import android.content.Context;
import android.os.AsyncTask;

import com.assignment.freshly.DatabaseConfig.DatabaseClient;
import com.assignment.freshly.Dao.ProductDao;
import com.assignment.freshly.Entity.Product;

public class UpdateProduct extends AsyncTask<Product, Void, Integer> {
    private Context context;
    private UpdateProductListener updateProductListener;

    public UpdateProduct(Context applicationContext, UpdateProductListener updateProductListener) {
        this.context = applicationContext;
        this.updateProductListener = updateProductListener;
    }

    @Override
    protected Integer doInBackground(Product... products) {
        ProductDao productDao = DatabaseClient.getInstance(context).freshlyDatabase.productDao();
        return productDao.update(products[0]);
    }

    @Override
    protected void onPostExecute(Integer result){
        if(result != 0){
            updateProductListener.onUpdateProductSuccess();
        }
        else {
            updateProductListener.onUpdateProductFailure();
        }
    }


    public interface UpdateProductListener{
        void onUpdateProductSuccess();
        void onUpdateProductFailure();
    }
}
