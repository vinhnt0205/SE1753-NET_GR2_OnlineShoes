package com.example.se1753net_gr2_onlineshoes.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.se1753net_gr2_onlineshoes.data.repository.OrderRepository;
import com.example.se1753net_gr2_onlineshoes.data.repository.ProductRepository;
import com.example.se1753net_gr2_onlineshoes.data.repository.StatisticRepository;
import com.example.se1753net_gr2_onlineshoes.viewmodel.marketing_viewmodel.MarketingAnalyticViewModel;

public class MarketingAnalyticViewModelFactory implements ViewModelProvider.Factory {
    private final OrderRepository orderRepository;
    private final StatisticRepository statisticRepository;
    private final ProductRepository productRepository;


    public MarketingAnalyticViewModelFactory(OrderRepository orderRepository,  StatisticRepository statisticRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.statisticRepository = statisticRepository;
        this.productRepository = productRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MarketingAnalyticViewModel.class)) {
            return (T) new MarketingAnalyticViewModel(orderRepository, statisticRepository, productRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}