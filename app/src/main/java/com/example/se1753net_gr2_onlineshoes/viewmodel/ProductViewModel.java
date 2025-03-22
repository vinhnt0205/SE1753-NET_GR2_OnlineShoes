package com.example.se1753net_gr2_onlineshoes.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.Product;
import com.example.se1753net_gr2_onlineshoes.data.repository.ProductRepository;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ProductViewModel extends ViewModel {
    private final ProductRepository repository;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<List<Product>> productList = new MutableLiveData<>();

    public ProductViewModel(ProductRepository repository) {
        this.repository = repository;
        loadProducts();
    }

    public LiveData<List<Product>> getProducts() {
        return productList;
    }

    private void loadProducts() {
        disposable.add(repository.getAllProducts()
                .subscribe(products -> productList.postValue(products),
                        throwable -> Log.e("ProductViewModel", "Error fetching products", throwable)));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear(); // Prevent memory leaks
    }
}

