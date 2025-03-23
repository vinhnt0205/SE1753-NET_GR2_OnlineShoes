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

    @Query("UPDATE Product_Statistics SET total_sold = " +
            "(SELECT COALESCE(SUM(quantity), 0) FROM Order_Details WHERE product_id = :productId) " +
            "WHERE product_id = :productId")
    void updateTotalSold(String productId);

    @Query("UPDATE Product_Statistics SET avg_rating = " +
            "(SELECT COALESCE(AVG(f.rating), 0) FROM Feedback f " +
            "JOIN Order_Details od ON f.order_detail_id = od.order_detail_id " +
            "WHERE od.product_id = :productId) " +
            "WHERE product_id = :productId")
    void updateAvgRating(String productId);
}

