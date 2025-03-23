package com.example.se1753net_gr2_onlineshoes.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.ProductImage;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface ProductImageDao {
    @Insert
    void insertImage(ProductImage image);

    @Delete
    void deleteImage(ProductImage image);

    @Query("SELECT * FROM Product_Images WHERE product_id = :productId")
    List<ProductImage> getImagesByProductId(String productId);

    @Query("SELECT image_url FROM Product_Images WHERE product_id = :productId LIMIT 1")
    LiveData<String> getFirstImageUrl(String productId);

    @Query("SELECT * FROM Product_Images WHERE image_id IN (SELECT MIN(image_id) FROM Product_Images GROUP BY product_id)")
    Single<List<ProductImage>> getFirstImagesForAllProducts();

}
