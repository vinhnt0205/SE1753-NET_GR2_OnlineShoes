package com.example.se1753net_gr2_onlineshoes.data.repository;

import com.example.se1753net_gr2_onlineshoes.data.local.dao.OrderDao;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.User;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CustomerRepository {
    private final OrderDao orderDao;

    public CustomerRepository(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    // 🟢 Lấy danh sách khách hàng có đơn hàng
    public Flowable<List<User>> getCustomersWithOrders() {
        return orderDao.getCustomersWithOrders();
    }

    // 🟢 Lấy toàn bộ danh sách khách hàng
    public Flowable<List<User>> getAllCustomers() {
        return orderDao.getAllCustomers();
    }

    // 🟢 Lấy thông tin chi tiết của khách hàng theo ID
    public Single<User> getCustomerById(String userId) {
        return orderDao.getCustomerById(userId);
    }

    // 🟢 Tìm kiếm khách hàng theo tên hoặc email
    public Flowable<List<User>> searchCustomers(String searchQuery) {
        return orderDao.searchCustomers(searchQuery);
    }

    // 🟢 Sắp xếp danh sách khách hàng
    public Flowable<List<User>> sortCustomers(String sortBy) {
        return orderDao.sortCustomers(sortBy);
    }

    // 🟢 Thêm hoặc cập nhật khách hàng
    public Completable insertOrUpdateCustomer(User customer) {
        return orderDao.insertOrUpdateCustomer(customer);
    }

    // 🟢 Cập nhật thông tin khách hàng
    public Completable updateCustomer(User customer) {
        return orderDao.updateCustomer(customer);
    }

    // 🟢 Xóa khách hàng
    public Completable deleteCustomer(User customer) {
        return orderDao.deleteCustomer(customer);
    }

}

