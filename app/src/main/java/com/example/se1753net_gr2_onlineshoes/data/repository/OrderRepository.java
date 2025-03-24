package com.example.se1753net_gr2_onlineshoes.data.repository;

import android.app.Application;

import com.example.se1753net_gr2_onlineshoes.data.local.dao.OrderDao;
import com.example.se1753net_gr2_onlineshoes.data.local.database.ShoeShopDatabase;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Order;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.OrderSummary;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class OrderRepository {
    private final OrderDao orderDao;

    public OrderRepository(Application application) {
        ShoeShopDatabase database = ShoeShopDatabase.getInstance(application);
        this.orderDao = database.orderDao();
    }

    public OrderRepository(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public Flowable<List<Order>> getAllOrders() {
        return orderDao.getAllOrders();
    }

    public Single<List<Order>> getOrdersByUserId(String userId) {
        return orderDao.getOrdersByUserId(userId);
    }

    public Single<Order> getOrderById(String orderId) {
        return orderDao.getOrderById(orderId);
    }

    public Single<List<Order>> getOrdersByStatus(String status) {
        return orderDao.getOrdersByStatus(status);
    }

    public Single<OrderSummary> getOrderSummary() {
        return orderDao.getOrderSummary();
    }

    public Completable insertOrder(Order order) {
        return orderDao.insertOrder(order);
    }

    public Completable updateOrder(Order order) {
        return orderDao.updateOrder(order);
    }

    public Completable deleteOrder(Order order) {
        return orderDao.deleteOrder(order);
    }
}