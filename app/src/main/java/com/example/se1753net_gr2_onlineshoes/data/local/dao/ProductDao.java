package com.example.se1753net_gr2_onlineshoes.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert
    void insertProduct(Product product);

    @Update
    void updateProduct(Product product);

    @Delete
    void deleteProduct(Product product);

    @Query("SELECT * FROM Products WHERE product_id = :productId")
    Product getProductById(String productId);

    @Query("SELECT * FROM Products")
    List<Product> getAllProducts();

    @Query("SELECT * FROM Products WHERE brand_id = :brandId")
    List<Product> getProductsByBrand(String brandId);

    @Query("SELECT * FROM Products WHERE status = 'Active'")
    List<Product> getActiveProducts();

    @Query("SELECT * FROM Products WHERE is_featured = 1")
    List<Product> getFeaturedProducts();
}

