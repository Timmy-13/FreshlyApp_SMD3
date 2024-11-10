package com.assignment.freshly.Dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.assignment.freshly.Entity.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert
    public Long insertCategory(Category category);

    @Query("SELECT * FROM category")
    LiveData<List<Category>> getAllCategories();

    @Query("SELECT * FROM category WHERE ca_id = :categoryId")
    LiveData<Category> getCategoryById(int categoryId);

    @Delete
    void deleteCategory(Category category);

    @Update
    void updateCategory(Category category);

    @Query("SELECT c.* FROM category c WHERE c.name =:category")
    public Category getCategoryByName(String category);
}
