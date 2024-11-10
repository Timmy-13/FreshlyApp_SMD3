package com.assignment.freshly.AsyncTask.Vendor;

import android.content.Context;
import android.os.AsyncTask;

import com.assignment.freshly.Dao.VendorDao;
import com.assignment.freshly.DatabaseConfig.DatabaseClient;

public class GetProfileImage extends AsyncTask<Integer, Void, byte[]> {
    private Context context;
    private GetProfileImageListener getProfileImageListener;

    public GetProfileImage(Context context, GetProfileImageListener getProfileImageListener){
        this.context = context;
        this.getProfileImageListener = getProfileImageListener;
    }

    @Override
    protected byte[] doInBackground(Integer... integers) {
        VendorDao vendorDao = DatabaseClient.getInstance(context).freshlyDatabase.vendorDao();
        return vendorDao.getVendorProfileImage(integers[0]);
    }

    @Override
    protected void onPostExecute(byte[] result){
        if(result == null){
            getProfileImageListener.onFailure();
        }
        else{
            getProfileImageListener.onSuccess(result);
        }
    }


    public interface GetProfileImageListener{
        void onSuccess(byte[] res);
        void onFailure();
    }
}
