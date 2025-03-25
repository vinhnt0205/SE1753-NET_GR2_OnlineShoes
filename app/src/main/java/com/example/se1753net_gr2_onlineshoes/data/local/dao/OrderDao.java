package com.example.se1753net_gr2_onlineshoes.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.Order;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.OrderSummary;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface OrderDao {
    @Insert
    Completable insertOrder(Order order); // ✅ Trả về Completable để xử lý bất đồng bộ khi insert

    @Update
    Completable updateOrder(Order order); // ✅ Trả về Completable để xử lý update

    @Delete
    Completable deleteOrder(Order order); // ✅ Trả về Completable để xử lý delete

    @Query("SELECT * FROM Orders")
    Flowable<List<Order>> getAllOrders(); // ✅ Flowable để lắng nghe thay đổi danh sách đơn hàng

    @Query("SELECT * FROM Orders WHERE user_id = :userId")
    Single<List<Order>> getOrdersByUserId(String userId); // ✅ Single để lấy danh sách đơn hàng theo user_id

    @Query("SELECT * FROM Orders WHERE order_id = :orderId")
    Single<Order> getOrderById(String orderId); // ✅ Single để lấy 1 đơn hàng duy nhất

    @Query("SELECT * FROM Orders WHERE status = :status ORDER BY order_date DESC")
    Single<List<Order>> getOrdersByStatus(String status); // ✅ Single để lấy danh sách đơn hàng theo trạng thái

    // ✅ Lấy tổng số đơn hàng và tổng doanh thu (Single vì chỉ có 1 kết quả)
    @Query("SELECT COUNT(*) as totalOrders, COALESCE(SUM(total_cost), 0) as totalRevenue FROM Orders")
    Single<OrderSummary> getOrderSummary();

    // ✅ Truy vấn danh sách đơn hàng với các bộ lọc (RxJava)
    @Query("SELECT * FROM Orders WHERE " +
            "(status = :status OR :status = 'All') AND " +
            "(order_id LIKE '%' || :keyword || '%' OR user_id LIKE '%' || :keyword || '%') AND " +
            "(order_date >= :fromDate OR :fromDate IS NULL) AND " +
            "(order_date <= :toDate OR :toDate IS NULL) " +
            "ORDER BY order_date DESC " +
            "LIMIT :limit")
    Single<List<Order>> getFilteredOrders(String keyword, String status, Date fromDate, Date toDate, int limit);
}
