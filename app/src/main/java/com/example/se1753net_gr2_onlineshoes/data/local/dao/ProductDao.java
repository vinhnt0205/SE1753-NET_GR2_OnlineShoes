package com.example.se1753net_gr2_onlineshoes.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.Product;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface ProductDao {
    @Insert
    void insertProduct(Product product);

    @Update
    void updateProduct(Product product);

    @Delete
    void deleteProduct(Product product);

    @Query("SELECT * FROM Products WHERE product_id = :productId")
    LiveData<Product> getProductById(String productId);

    @Query("UPDATE Products SET name = :name, description = :description, price = :price, status = :status WHERE product_id = :productId")
    void updateProduct(String productId, String name, String description, double price, String status);

    @Query("SELECT * FROM Products")
    Flowable<List<Product>> getAllProducts();

    @Query("SELECT * FROM Products WHERE brand_id = :brandId")
    List<Product> getProductsByBrand(String brandId);

    @Query("SELECT * FROM Products WHERE status = 'Active'")
    List<Product> getActiveProducts();

    @Query("SELECT * FROM Products WHERE is_featured = 1")
    List<Product> getFeaturedProducts();
}

