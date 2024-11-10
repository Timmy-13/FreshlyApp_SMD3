package com.assignment.freshly.AsyncTask.Customer;

import android.content.Context;
import android.os.AsyncTask;

import com.assignment.freshly.DatabaseConfig.DatabaseClient;
import com.assignment.freshly.Dao.CustomerDao;
import com.assignment.freshly.Entity.Customer;

public class InsertCustomer extends AsyncTask<Customer, Void, Long> {
    private Context context;
    private CustomerSignupListener customerSignupListener;

    public InsertCustomer(Context context, CustomerSignupListener customerSignupListener) {
        this.context = context;
        this.customerSignupListener = customerSignupListener;
    }

    @Override
    protected Long doInBackground(Customer... customers) {
        CustomerDao customerDao = DatabaseClient.getInstance(context).freshlyDatabase.customerDao();
        Customer existentCustomer = customerDao.getCustomerByEmail(customers[0].getEmail());
        if(existentCustomer == null){
            return customerDao.insert(customers[0]);
        }
        else{
            return -1L;
        }
    }

    @Override
    protected  void onPostExecute(Long result){
        if(result != -1){
            customerSignupListener.onSignUpSuccess();
        }
        else{
            customerSignupListener.onSignUpFailure();
        }
    }

    public interface CustomerSignupListener{
        void onSignUpSuccess();
        void onSignUpFailure();
    }
}
