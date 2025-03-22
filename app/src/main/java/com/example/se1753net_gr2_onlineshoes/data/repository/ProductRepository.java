package com.example.se1753net_gr2_onlineshoes.data.repository;

import com.example.se1753net_gr2_onlineshoes.data.local.dao.ProductDao;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Product;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProductRepository {
    private final ProductDao productDao;

    public ProductRepository(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Flowable<List<Product>> getAllProducts() {
        return productDao.getAllProducts()
                .subscribeOn(Schedulers.io()) // Run on background thread
                .observeOn(AndroidSchedulers.mainThread()); // Observe on UI thread
    }
}
