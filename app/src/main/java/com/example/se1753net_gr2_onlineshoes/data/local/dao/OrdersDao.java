package com.example.se1753net_gr2_onlineshoes.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.Order;

import java.util.List;

@Dao
public interface OrdersDao {
    @Insert
    void insertOrder(Order order);

    @Update
    void updateOrder(Order order);

    @Delete
    void deleteOrder(Order order);

    @Query("SELECT * FROM Orders WHERE user_id = :userId ORDER BY order_date DESC")
    List<Order> getOrdersByUser(String userId);

    @Query("SELECT * FROM Orders WHERE status = :status ORDER BY order_date DESC")
    List<Order> getOrdersByStatus(String status);

    @Query("SELECT * FROM Orders WHERE order_id = :orderId")
    Order getOrderById(String orderId);
}
