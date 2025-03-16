package com.example.se1753net_gr2_onlineshoes.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.ProductStatistics;

import java.util.List;

@Dao
public interface ProductStatisticsDao {
    @Insert
    void insertProductStatistics(ProductStatistics stats);

    @Update
    void updateProductStatistics(ProductStatistics stats);

    @Query("SELECT * FROM Product_Statistics WHERE product_id = :productId")
    ProductStatistics getStatsByProductId(String productId);
}

