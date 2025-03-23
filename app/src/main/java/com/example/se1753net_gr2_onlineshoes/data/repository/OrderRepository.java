package com.example.se1753net_gr2_onlineshoes.data.repository;

import android.app.Application;

import com.example.se1753net_gr2_onlineshoes.data.local.dao.OrderDao;
import com.example.se1753net_gr2_onlineshoes.data.local.database.ShoeShopDatabase;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Order;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.OrderSummary;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class OrderRepository {
    private final OrderDao orderDao;

    // Primary constructor (from main) - Uses Application to initialize DAO
    public OrderRepository(Application application) {
        ShoeShopDatabase database = ShoeShopDatabase.getInstance(application);
        this.orderDao = database.orderDao();
    }

    // Secondary constructor (from Vinhnq) - Accepts OrderDao directly
    public OrderRepository(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    // Method from main - Fetch all orders as Flowable
    public Flowable<List<Order>> getAllOrders() {
        return orderDao.getAllOrders();
    }

    // Method from Vinhnq - Fetch order summary
    public OrderSummary getOrderSummary() {
        return orderDao.getOrderSummary();
    }
}
