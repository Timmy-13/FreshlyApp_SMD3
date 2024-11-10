package com.assignment.freshly.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.assignment.freshly.Entity.Customer;

@Dao
public interface CustomerDao {

    @Insert
    public Long insert(Customer customer);

    @Query("SELECT c.* FROM customer c WHERE c.email =:email")
    public Customer getCustomerByEmail(String email);

    @Query("SELECT * FROM customer WHERE email = :email AND password = :password")
    public Customer getCustomerByEmailAndPassword(String email, String password);
}
