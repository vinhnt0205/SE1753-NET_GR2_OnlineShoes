package com.example.se1753net_gr2_onlineshoes.data.repository;

import android.app.Application;

import com.example.se1753net_gr2_onlineshoes.data.local.dao.OrderDao;
import com.example.se1753net_gr2_onlineshoes.data.local.database.ShoeShopDatabase;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Order;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Product;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class OrderRepository {
    private final OrderDao orderDao;

    public OrderRepository(Application application) {
        ShoeShopDatabase database = ShoeShopDatabase.getInstance(application);
        this.orderDao = database.orderDao();
    }

    public Flowable<List<Order>> getAllOrders() {
        return orderDao.getAllOrders();

    }
}
