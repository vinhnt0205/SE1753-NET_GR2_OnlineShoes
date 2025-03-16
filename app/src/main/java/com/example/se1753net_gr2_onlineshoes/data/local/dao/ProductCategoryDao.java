package com.example.se1753net_gr2_onlineshoes.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.ProductCategory;

import java.util.List;

@Dao
public interface ProductCategoryDao {
    @Insert
    void insertProductCategory(ProductCategory productCategory);

    @Delete
    void deleteProductCategory(ProductCategory productCategory);

    @Query("SELECT * FROM Product_Categories WHERE product_id = :productId")
    List<ProductCategory> getCategoriesByProduct(String productId);

    @Query("SELECT * FROM Product_Categories WHERE category_id = :categoryId")
    List<ProductCategory> getProductsByCategory(String categoryId);
}
