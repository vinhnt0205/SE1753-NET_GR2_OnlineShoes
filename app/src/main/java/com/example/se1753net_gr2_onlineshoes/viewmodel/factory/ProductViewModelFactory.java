package com.example.se1753net_gr2_onlineshoes.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.se1753net_gr2_onlineshoes.data.repository.ProductRepository;
import com.example.se1753net_gr2_onlineshoes.viewmodel.ProductViewModel;

public class ProductViewModelFactory implements ViewModelProvider.Factory {
    private final ProductRepository repository;

    public ProductViewModelFactory(ProductRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProductViewModel.class)) {
            return (T) new ProductViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

