package com.example.se1753net_gr2_onlineshoes.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.se1753net_gr2_onlineshoes.data.repository.ProductRepository;
import com.example.se1753net_gr2_onlineshoes.data.repository.SliderRepository;
import com.example.se1753net_gr2_onlineshoes.viewmodel.marketing_viewmodel.MarketingProductListViewModel;
import com.example.se1753net_gr2_onlineshoes.viewmodel.marketing_viewmodel.MarketingSliderListViewModel;

public class MarketingSliderListViewModelFactory implements ViewModelProvider.Factory {
    private final SliderRepository repository;

    public MarketingSliderListViewModelFactory(SliderRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MarketingSliderListViewModel.class)) {
            return (T) new MarketingSliderListViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
