package com.assignment.freshly.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.assignment.freshly.Entity.Product;
import com.assignment.freshly.Entity.Vendor;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert
    public Long insert(Product product);

    @Delete
    public void delete(Product product);

    @Update
    public int update(Product product);

    @Query("SELECT * FROM product WHERE title LIKE '%' || :searchQuery || '%' OR description LIKE '%' || :searchQuery || '%'")
    public LiveData<List<Product>> searchProducts(String searchQuery);

    @Query("SELECT p.* FROM product p JOIN category c on p.category_id = c.ca_id WHERE c.name=:category AND p.vendor_id=:vendorId")
    List<Product> getProductsByCategoryAndVendor(String category, int vendorId);

    @Query("SELECT p.* FROM product p WHERE p.p_id =:productId")
    public Product getProductById(int productId);

    @Query("SELECT p.* FROM vendor v JOIN product p ON v.v_id = p.vendor_id WHERE v.v_id =:vendorId")
    public List<Product> getAllVendorProducts(int vendorId);

    @Query("SELECT p.* FROM product p LIMIT 15")
    public List<Product> getAllProducts();

    @Query("SELECT p.* FROM product p JOIN category c ON C.ca_id = p.category_id WHERE c.name =:categoryName")
    public List<Product> getProductsByCategory(String categoryName);



}
