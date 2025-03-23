package com.example.se1753net_gr2_onlineshoes.viewmodel.factory;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.se1753net_gr2_onlineshoes.data.repository.OrderRepository;
import com.example.se1753net_gr2_onlineshoes.viewmodel.sale_viewmodel.SalesOverviewViewModel;
public class SalesOverviewViewModelFactory implements ViewModelProvider.Factory {
    private final OrderRepository orderRepository;

    public SalesOverviewViewModelFactory(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SalesOverviewViewModel.class)) {
            return (T) new SalesOverviewViewModel(orderRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}