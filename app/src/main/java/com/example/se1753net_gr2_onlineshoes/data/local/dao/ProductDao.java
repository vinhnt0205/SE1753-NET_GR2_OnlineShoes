package com.example.se1753net_gr2_onlineshoes.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.Product;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.ProductWithImages;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
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

//    @Query("SELECT p.product_id, p.name, p.description, p.price, p.sale_price,p.brand_id,p.created_at, p.updated_at,p.stock, p.status, pi.image_url " +
//            "FROM Products p " +
//            "LEFT JOIN Product_Images pi ON p.product_id = pi.product_id " +
//            "WHERE p.status = 'Active'")
    List<Product> getActiveProducts();

    @Query("SELECT * FROM Products WHERE is_featured = 1")
    List<Product> getFeaturedProducts();

    @Transaction
//    @Query("SELECT p.product_id, p.name,p.price,p.stock, p.description, p.sale_price, pi.image_url " +
//            "FROM Products p " +
//            "LEFT JOIN Product_Images pi ON p.product_id = pi.product_id " +
//            "WHERE p.status = 'Active'")
    @Query("SELECT * FROM Products WHERE status = 'Active'")
    List<ProductWithImages> getAllProductsWithImages();
}

