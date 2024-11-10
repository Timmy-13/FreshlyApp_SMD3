package com.assignment.freshly.AsyncTask.Product;

import android.content.Context;
import android.os.AsyncTask;

import com.assignment.freshly.DatabaseConfig.DatabaseClient;
import com.assignment.freshly.Dao.ProductDao;
import com.assignment.freshly.Entity.Product;

public class DeleteProduct extends AsyncTask<Product, Void, Void> {
    private Context context;
    private DeleteProductListener deleteProductListener;

    public DeleteProduct(Context context, DeleteProductListener deleteProductListener) {
        this.context = context;
        this.deleteProductListener = deleteProductListener;
    }

    @Override
    protected Void doInBackground(Product... products) {
        ProductDao productDao = DatabaseClient.getInstance(context).freshlyDatabase.productDao();
        productDao.delete(products[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        deleteProductListener.DeleteProductSuccess();
    }

    public interface DeleteProductListener{
        void DeleteProductSuccess();
        void DeleteProductFailure();
    }
}
