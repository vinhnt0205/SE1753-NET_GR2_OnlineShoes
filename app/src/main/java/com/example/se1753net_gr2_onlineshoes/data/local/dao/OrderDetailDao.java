package com.example.se1753net_gr2_onlineshoes.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Update;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.OrderDetail;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

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
    @Update
    Completable updateOrderDetail(OrderDetail orderDetail); // RxJava Completable

    @Query("SELECT * FROM Order_Details WHERE order_detail_id = :orderDetailId")
    Single<OrderDetail> getOrderDetailById(String orderDetailId);
}

