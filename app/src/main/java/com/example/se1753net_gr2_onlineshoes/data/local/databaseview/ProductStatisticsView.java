package com.example.se1753net_gr2_onlineshoes.data.local.databaseview;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.DatabaseView;


@DatabaseView("SELECT " +
        "p.product_id AS product_id, " +
        "COALESCE(ps.views, 0) AS views, " +
        "COALESCE(SUM(od.quantity), 0) AS total_sold, " +
        "COALESCE(AVG(f.rating), 0) AS avg_rating, " +
        "p.created_at AS created_at " +
        "FROM Products p " +
        "LEFT JOIN Product_Statistics ps ON p.product_id = ps.product_id " +
        "LEFT JOIN Order_Details od ON p.product_id = od.product_id " +
        "LEFT JOIN Feedback f ON od.order_detail_id = f.order_detail_id " +  // 👈 Join through Order_Details
        "GROUP BY p.product_id")
public class ProductStatisticsView {
    @NonNull
    @ColumnInfo(name = "product_id")
    public String productId;

    @ColumnInfo(name = "views")
    public int views;

    @ColumnInfo(name = "total_sold")
    public int totalSold;

    @ColumnInfo(name = "avg_rating")
    public Double avgRating;

    @ColumnInfo(name = "created_at")
    public String createdAt;
}


