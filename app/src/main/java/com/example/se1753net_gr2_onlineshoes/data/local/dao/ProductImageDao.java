package com.example.se1753net_gr2_onlineshoes.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.ProductImage;

import java.util.List;

@Dao
public interface ProductImageDao {
    @Insert
    void insertImage(ProductImage image);

    @Delete
    void deleteImage(ProductImage image);

    @Query("SELECT * FROM Product_Images WHERE product_id = :productId")
    List<ProductImage> getImagesByProductId(String productId);
}
