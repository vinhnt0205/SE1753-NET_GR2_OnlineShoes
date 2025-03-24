package com.example.se1753net_gr2_onlineshoes.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.se1753net_gr2_onlineshoes.data.local.dao.ProductDao;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.ProductImageDao;
import com.example.se1753net_gr2_onlineshoes.data.local.database.ShoeShopDatabase;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Product;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.ProductImage;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProductRepository {
    private final ProductDao productDao;
    private final ProductImageDao productImageDao;

    public ProductRepository(Application application) {
        ShoeShopDatabase database = ShoeShopDatabase.getInstance(application);
        this.productDao = database.productDao();
        this.productImageDao = database.productImageDao();
    }

    public Flowable<List<Product>> getAllProducts() {
        return productDao.getAllProducts();
    }

    public LiveData<String> getFirstImageUrl(String productId) {
        return productImageDao.getFirstImageUrl(productId);
    }

    public Single<List<ProductImage>> getFirstImagesForAllProducts() {
        return productImageDao.getFirstImagesForAllProducts();
    }
}
