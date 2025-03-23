package com.example.se1753net_gr2_onlineshoes.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.Order;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface OrderDao {
    @Insert
    void insertOrder(Order order);

    @Update
    void updateOrder(Order order);

    @Delete
    void deleteOrder(Order order);

    @Query("SELECT * FROM Orders")
    Flowable<List<Order>> getAllOrders();

    @Query("SELECT * FROM Orders WHERE user_id = :userId")
    List<Order> getOrdersByUserId(String userId);

    @Query("SELECT * FROM Orders WHERE order_id = :orderId")
    Order getOrderById(String orderId);
}

