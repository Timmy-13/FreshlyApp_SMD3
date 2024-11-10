package com.assignment.freshly.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "product",
        foreignKeys = {@ForeignKey(
                entity = Vendor.class,
                parentColumns = "v_id",
                childColumns = "vendor_id",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        ),
                @ForeignKey(
                  entity = Category.class,
                  parentColumns = "ca_id",
                  childColumns = "category_id",
                  onUpdate = ForeignKey.CASCADE,
                  onDelete = ForeignKey.CASCADE
                )
        }
)
public class Product {
    @PrimaryKey(autoGenerate = true)
    private int p_id;
    private String title;
    private String description;
    private int category_id;
    private int vendor_id;
    private byte[] imagePath;


    public Product(String title, String description, int category_id, int vendor_id, byte[] imagePath) {
        this.title = title;
        this.description = description;
        this.category_id = category_id;
        this.vendor_id = vendor_id;
        this.imagePath = imagePath;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public String getTitle() {
        return title;
    }

    public byte[] getImagePath() {
        return imagePath;
    }

    public void setImagePath(byte[] imagePath) {
        this.imagePath = imagePath;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(int vendor_id) {
        this.vendor_id = vendor_id;
    }
}
