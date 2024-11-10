package com.assignment.freshly.AsyncTask.Vendor;

import android.content.Context;
import android.os.AsyncTask;

import com.assignment.freshly.Dao.VendorDao;
import com.assignment.freshly.DatabaseConfig.DatabaseClient;
import com.assignment.freshly.Entity.Vendor;

public class GetVendorByUsernameAndPassword extends AsyncTask<String, Void, Vendor> {
    private Context context;
    private GetCustomerListener getCustomerListener;

    public GetVendorByUsernameAndPassword(Context context, GetCustomerListener getCustomerListener){
        this.context = context;
        this.getCustomerListener = getCustomerListener;
    }
    @Override
    protected Vendor doInBackground(String... strings) {
        String username = strings[0];
        String password = strings[1];
        VendorDao vendorDao = DatabaseClient.getInstance(context).freshlyDatabase.vendorDao();
        return vendorDao.authenticateVendor(username, password);
    }

    @Override
    public void onPostExecute(Vendor vendor){
        if(vendor == null){
            getCustomerListener.GetCustomerListenerFailure();
        }
        else{
            getCustomerListener.GetCustomerListenerSuccess(vendor.getV_id());
        }
    }

    public interface GetCustomerListener{
        void GetCustomerListenerSuccess(int id);
        void GetCustomerListenerFailure();
    }
}
