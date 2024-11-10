package com.assignment.freshly.AsyncTask.Vendor;

import android.content.Context;
import android.os.AsyncTask;

import com.assignment.freshly.DatabaseConfig.DatabaseClient;
import com.assignment.freshly.Dao.VendorDao;
import com.assignment.freshly.Entity.Vendor;

public class InsertVendor extends AsyncTask<Vendor, Void, Long> {
    private Context context;
    private InsertVendorListener insertVendorListener;

    public InsertVendor(Context context, InsertVendorListener insertVendorListener){
        this.context = context;
        this.insertVendorListener = insertVendorListener;
    }


    @Override
    protected Long doInBackground(Vendor... vendors) {
        VendorDao vendorDao = DatabaseClient.getInstance(context).freshlyDatabase.vendorDao();
        Vendor existentVendor = vendorDao.getVendorById(vendors[0].getUsername());
        if(existentVendor == null){
            return vendorDao.insertVendor(vendors[0]);
        }
        else{
            return -1L;
        }
    }

    @Override
    protected void onPostExecute(Long result){
        if(result == -1){
            insertVendorListener.insertListenerFailure();
        }
        else{
            insertVendorListener.insertListenerSuccess();
        }
    }

    public interface InsertVendorListener{
        void insertListenerSuccess();
        void insertListenerFailure();
    }
}
