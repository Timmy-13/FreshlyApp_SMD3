package com.assignment.freshly.Activities.Vendor;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.assignment.freshly.Dao.VendorDao;
import com.assignment.freshly.DatabaseConfig.DatabaseClient;
import com.assignment.freshly.Entity.Vendor;

public class UpdateVendor extends AsyncTask<Vendor, Void, Integer> {
    private Context context;
    private UpdateVendorListener updateVendorListener;

    public UpdateVendor(Context context, UpdateVendorListener updateVendorListener){
        this.context = context;
        this.updateVendorListener = updateVendorListener;
    }

    @Override
    protected Integer doInBackground(Vendor... vendors) {
        VendorDao vendorDao = DatabaseClient.getInstance(context).freshlyDatabase.vendorDao();
        return vendorDao.updateVendor(vendors[0]);
    }

    @Override
    protected void onPostExecute(Integer result){
        if(result == 0){
            updateVendorListener.onFailure();
        }
        else{
            updateVendorListener.onSuccess();
        }
    }


    public interface UpdateVendorListener{
        void onSuccess();
        void onFailure();
    }
}
