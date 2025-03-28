package com.example.se1753net_gr2_onlineshoes.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.room.DatabaseView;

import com.example.se1753net_gr2_onlineshoes.data.local.databaseview.ProductStatisticsView;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.ProductStatistics;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

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

    @Query("SELECT * FROM Product_Statistics WHERE product_id = :productId ORDER BY created_at ASC")
    Flowable<List<ProductStatistics>> getStatisticsByProductId(String productId);

    @Query("SELECT * FROM ProductStatisticsView")
    Flowable<List<ProductStatisticsView>> getProductStatisticsView();
}

