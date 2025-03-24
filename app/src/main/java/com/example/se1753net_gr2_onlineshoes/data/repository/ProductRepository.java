package com.example.se1753net_gr2_onlineshoes.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.se1753net_gr2_onlineshoes.data.local.dao.ProductDao;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.ProductImageDao;
import com.example.se1753net_gr2_onlineshoes.data.local.database.ShoeShopDatabase;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Product;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.ProductImage;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
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

    // Get all products
    public Flowable<List<Product>> getAllProducts() {
        return productDao.getAllProducts();
    }

    // Get first image URL for a product
    public LiveData<String> getFirstImageUrl(String productId) {
        return productImageDao.getFirstImageUrl(productId);
    }

    // Get all first images for products
    public Single<List<ProductImage>> getFirstImagesForAllProducts() {
        return productImageDao.getFirstImagesForAllProducts();
    }

    // **Get the first product image by product ID**
    public Single<ProductImage> getProductImageById(String productId) {
        return productImageDao.getProductSingleImageById(productId);
    }

    public LiveData<Product> getProductById(String productId) {
        return productDao.getProductById(productId);
    }

    // **Update product using RxJava (Completable)**
    public Completable updateProduct(String productId, String name, String description, double price, String status) {
        return Completable.fromAction(() ->
                productDao.updateProduct(productId, name, description, price, status)
        ).subscribeOn(Schedulers.io()); // Run in background thread
    }
}

