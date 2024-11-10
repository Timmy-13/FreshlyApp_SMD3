package com.assignment.freshly.DatabaseConfig;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.assignment.freshly.Dao.CategoryDao;
import com.assignment.freshly.Dao.CustomerDao;
import com.assignment.freshly.Dao.ProductDao;
import com.assignment.freshly.Dao.VendorDao;
import com.assignment.freshly.Entity.Category;
import com.assignment.freshly.Entity.Customer;
import com.assignment.freshly.Entity.Product;
import com.assignment.freshly.Entity.Vendor;

@Database(entities = {Product.class, Category.class, Customer.class, Vendor.class}, version = 1)
public abstract class FreshlyDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
    public abstract VendorDao vendorDao();
    public abstract CategoryDao categoryDao();
    public abstract CustomerDao customerDao();
}
