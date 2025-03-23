package com.example.se1753net_gr2_onlineshoes.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.Order;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.OrderSummary; // ✅ IMPORT ĐÚNG CLASS

import java.util.Date;
import java.util.List;

@Dao
public interface OrderDao {
    @Insert
    void insertOrder(Order order);

    @Update
    void updateOrder(Order order);

    @Delete
    void deleteOrder(Order order);

    @Query("SELECT * FROM Orders WHERE user_id = :userId")
    List<Order> getOrdersByUserId(String userId);

    @Query("SELECT * FROM Orders WHERE order_id = :orderId")
    Order getOrderById(String orderId);

    @Query("SELECT * FROM Orders WHERE status = :status ORDER BY order_date DESC")
    List<Order> getOrdersByStatus(String status);


    // ✅ Truy vấn lấy tổng số đơn hàng và tổng doanh thu
    @Query("SELECT COUNT(*) as totalOrders, COALESCE(SUM(total_cost), 0) as totalRevenue FROM Orders")
    OrderSummary getOrderSummary();
    @Query("SELECT * FROM Orders WHERE " +
            "(status = :status OR :status = 'All') AND " +
            "(order_id LIKE '%' || :keyword || '%' OR user_id LIKE '%' || :keyword || '%') AND " +
            "(order_date >= :fromDate OR :fromDate IS NULL) AND " +
            "(order_date <= :toDate OR :toDate IS NULL) " +
            "ORDER BY order_date DESC " +
            "LIMIT :limit")
    List<Order> getFilteredOrders(String keyword, String status, Date fromDate, Date toDate, int limit);

}
