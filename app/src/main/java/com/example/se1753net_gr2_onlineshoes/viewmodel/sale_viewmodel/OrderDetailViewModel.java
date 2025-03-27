package com.example.se1753net_gr2_onlineshoes.viewmodel.sale_viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.se1753net_gr2_onlineshoes.data.local.dao.OrderDetailDao;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.OrderDetail;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class OrderDetailViewModel extends ViewModel {
    private OrderDetailDao orderDetailDao;

    public OrderDetailViewModel(OrderDetailDao orderDetailDao) {
        this.orderDetailDao = orderDetailDao;
    }

    public Single<OrderDetail> getOrderDetailById(String orderDetailId) {
        return orderDetailDao.getOrderDetailById(orderDetailId);
    }

    public Completable updateOrderDetail(OrderDetail orderDetail) {
        return orderDetailDao.updateOrderDetail(orderDetail);
    }
}