package com.example.se1753net_gr2_onlineshoes.data.local.databaseview;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.DatabaseView;

@DatabaseView(
        viewName = "Product_Statistics_View",
        value = "SELECT " +
                "p.product_id, " +
                "COUNT(ca.activity_id) AS views, " +
                "SUM(od.quantity) AS total_sold, " +
                "AVG(f.rating) AS avg_rating, " +
                "MIN(p.created_at) AS created_at " +
                "FROM Products p " +
                "LEFT JOIN Customer_Activity ca ON p.product_id = ca.product_id AND ca.activity_type = 'View' " +
                "LEFT JOIN Order_Details od ON p.product_id = od.product_id " +
                "LEFT JOIN Feedback f ON f.order_detail_id = od.order_detail_id " +
                "GROUP BY p.product_id"
    )
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

