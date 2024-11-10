package com.assignment.freshly.AsyncTask.Vendor;

import android.content.Context;
import android.os.AsyncTask;

import com.assignment.freshly.Dao.VendorDao;
import com.assignment.freshly.DatabaseConfig.DatabaseClient;
import com.assignment.freshly.Entity.Vendor;

public class GetVendorById extends AsyncTask<Integer, Void, Vendor> {
    private Context context;
    private GetVendorByIdListener getVendorByIdListener;

    public GetVendorById(Context context, GetVendorByIdListener getVendorByIdListener){
        this.context = context;
        this.getVendorByIdListener = getVendorByIdListener;
    }

    @Override
    protected Vendor doInBackground(Integer... integers) {
        VendorDao vendorDao = DatabaseClient.getInstance(context).freshlyDatabase.vendorDao();
        return vendorDao.getVendorById(integers[0]);
    }

    @Override
    protected void onPostExecute(Vendor vendor){
        if(vendor == null){
            getVendorByIdListener.onFailure();
        }
        else{
            getVendorByIdListener.onSuccess(vendor);
        }
    }


    public interface GetVendorByIdListener{
        void onSuccess(Vendor vendor);
        void onFailure();
    }
}
