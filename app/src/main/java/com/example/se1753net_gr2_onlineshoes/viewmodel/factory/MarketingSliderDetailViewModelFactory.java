package com.example.se1753net_gr2_onlineshoes.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.se1753net_gr2_onlineshoes.data.repository.SliderRepository;
import com.example.se1753net_gr2_onlineshoes.viewmodel.marketing_viewmodel.MarketingSliderDetailViewModel;
import com.example.se1753net_gr2_onlineshoes.viewmodel.marketing_viewmodel.MarketingSliderListViewModel;

public class MarketingSliderDetailViewModelFactory implements ViewModelProvider.Factory {
    private final SliderRepository repository;
    private final String sliderId;

    public MarketingSliderDetailViewModelFactory(SliderRepository repository, String sliderId) {
        this.repository = repository;
        this.sliderId = sliderId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MarketingSliderDetailViewModel.class)) {
            return (T) new MarketingSliderDetailViewModel(repository, sliderId);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}