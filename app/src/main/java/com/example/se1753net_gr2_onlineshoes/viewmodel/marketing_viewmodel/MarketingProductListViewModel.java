package com.example.se1753net_gr2_onlineshoes.viewmodel.marketing_viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.Product;
import com.example.se1753net_gr2_onlineshoes.data.repository.ProductRepository;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MarketingProductListViewModel extends ViewModel {
    private final ProductRepository repository;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<List<Product>> productListLiveData = new MutableLiveData<>();

    public MarketingProductListViewModel(ProductRepository repository) {
        this.repository = repository;
        loadProducts();
    }

    public LiveData<List<Product>> getProducts() {
        return productListLiveData;
    }

    private void loadProducts() {
        disposable.add(repository.getAllProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        products -> {
                            Log.d("MarketingProductListViewModel", "Products fetched: " + products.size());
                            for (Product product : products) {
                                Log.d("MarketingProductListViewModel", "Product: " + product.brandId + ", Name: " + product.name);
                            }
                            productListLiveData.setValue(products);
                        },
                        throwable -> {
                            Log.e("MarketingProductListViewModel", "Error fetching products", throwable);
                        }
                ));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear(); // Prevent memory leaks
    }
}

