package com.example.se1753net_gr2_onlineshoes.data.repository;

import com.example.se1753net_gr2_onlineshoes.data.local.dao.OrderDao;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.OrderSummary;

public class OrderRepository {
    private final OrderDao orderDao;

    public OrderRepository(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public OrderSummary getOrderSummary() {
        return orderDao.getOrderSummary();
    }
}
