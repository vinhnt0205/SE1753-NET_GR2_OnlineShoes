package com.example.se1753net_gr2_onlineshoes.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.OrderDetail;

import java.util.List;

@Dao
public interface OrderDetailDao {
    @Insert
    void insertOrderDetail(OrderDetail orderDetail);

    @Delete
    void deleteOrderDetail(OrderDetail orderDetail);

    @Query("SELECT * FROM Order_Details WHERE order_id = :orderId")
    List<OrderDetail> getOrderDetailsByOrderId(String orderId);

    @Query("SELECT * FROM Order_Details WHERE product_id = :productId")
    List<OrderDetail> getOrderDetailsByProductId(String productId);
}

