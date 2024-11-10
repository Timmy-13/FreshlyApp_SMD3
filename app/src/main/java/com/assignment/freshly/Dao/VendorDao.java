package com.assignment.freshly.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.assignment.freshly.Entity.Vendor;

import java.util.List;

@Dao
public interface VendorDao {
    @Insert
    public Long insertVendor(Vendor vendor);

    @Update
    public int updateVendor(Vendor vendor);

    @Delete
    public void deleteVendor(Vendor vendor);

    @Query("SELECT v.* FROM vendor v WHERE v.username =:username")
    public Vendor getVendorById(String username);

    @Query("SELECT v.* FROM vendor v WHERE v.username = :username AND v.password = :password LIMIT 1")
    public Vendor authenticateVendor(String username, String password);

    @Query("SELECT v.* FROM vendor v WHERE v.v_id = :vendorId")
    public Vendor getVendorById(int vendorId);

    @Query("SELECT * FROM vendor")
    LiveData<List<Vendor>> getAllVendors();

    @Query("SELECT v.imagePath FROM vendor v WHERE v.v_id =:vendorId")
    public byte[] getVendorProfileImage(int vendorId);


}
