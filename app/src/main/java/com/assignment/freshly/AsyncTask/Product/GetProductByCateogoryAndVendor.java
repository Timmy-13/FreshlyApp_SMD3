package com.assignment.freshly.AsyncTask.Product;

import android.content.Context;
import android.os.AsyncTask;

import com.assignment.freshly.DatabaseConfig.DatabaseClient;
import com.assignment.freshly.Dao.ProductDao;
import com.assignment.freshly.Entity.Product;

import java.util.List;

public class GetProductByCateogoryAndVendor extends AsyncTask<Object, Void, List<Product>> {
    private Context context;
    private returnCategoryProduct returnCategoryProduct;

    public GetProductByCateogoryAndVendor(Context context, returnCategoryProduct returnCategoryProduct){
        this.context = context;
        this.returnCategoryProduct = returnCategoryProduct;
    }
    @Override
    protected List<Product> doInBackground(Object[] objects) {
        Integer vendorId = (Integer)objects[0];
        String category = (String) objects[1];

        ProductDao productDao = DatabaseClient.getInstance(context).freshlyDatabase.productDao();
        return productDao.getProductsByCategoryAndVendor(category, vendorId);
    }

    @Override
    protected void onPostExecute(List<Product> products){
        if(products != null){
            returnCategoryProduct.onSuccess(products);
        }
        else{
            returnCategoryProduct.onFailure();
        }
    }

    public interface returnCategoryProduct {
        void onSuccess(List<Product> products);
        void onFailure();
    }
}
