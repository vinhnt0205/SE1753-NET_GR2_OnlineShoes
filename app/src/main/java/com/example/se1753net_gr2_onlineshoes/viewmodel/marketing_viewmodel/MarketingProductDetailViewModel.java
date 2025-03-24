package com.example.se1753net_gr2_onlineshoes.viewmodel.marketing_viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.Product;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.ProductImage;
import com.example.se1753net_gr2_onlineshoes.data.repository.ProductRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MarketingProductDetailViewModel extends ViewModel {
    private final ProductRepository repository;

    public MarketingProductDetailViewModel(ProductRepository repository) {
        this.repository = repository;
    }

    // Get product details
    public LiveData<Product> getProductById(String productId) {
        return repository.getProductById(productId);
    }

    // Get product image
    public Single<ProductImage> getProductImage(String productId) {
        return repository.getProductImageById(productId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    // Update product details
    public Completable updateProduct(String productId, String name, String description, double price, String status) {
        return repository.updateProduct(productId, name, description, price, status)
                .observeOn(AndroidSchedulers.mainThread()); // Ensure UI updates properly
    }
}
