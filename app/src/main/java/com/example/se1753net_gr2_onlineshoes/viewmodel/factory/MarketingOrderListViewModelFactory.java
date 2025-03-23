package com.example.se1753net_gr2_onlineshoes.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.se1753net_gr2_onlineshoes.data.repository.OrderRepository;
import com.example.se1753net_gr2_onlineshoes.data.repository.ProductRepository;
import com.example.se1753net_gr2_onlineshoes.viewmodel.marketing_viewmodel.MarketingOrderListViewModel;
import com.example.se1753net_gr2_onlineshoes.viewmodel.marketing_viewmodel.MarketingProductListViewModel;

public class MarketingOrderListViewModelFactory implements ViewModelProvider.Factory {
    private final OrderRepository repository;

    public MarketingOrderListViewModelFactory(OrderRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MarketingOrderListViewModel.class)) {
            return (T) new MarketingOrderListViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}