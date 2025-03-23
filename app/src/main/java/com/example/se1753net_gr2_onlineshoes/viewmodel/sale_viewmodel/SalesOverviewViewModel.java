package com.example.se1753net_gr2_onlineshoes.viewmodel.sale_viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.OrderSummary;
import com.example.se1753net_gr2_onlineshoes.data.repository.OrderRepository;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SalesOverviewViewModel extends ViewModel {
    private final OrderRepository orderRepository;
    private final MutableLiveData<OrderSummary> orderSummary = new MutableLiveData<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public SalesOverviewViewModel(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        loadOrderSummary();
    }

    private void loadOrderSummary() {
        executorService.execute(() -> {
            OrderSummary summary = orderRepository.getOrderSummary();
            orderSummary.postValue(summary);
        });
    }

    public LiveData<OrderSummary> getOrderSummary() {
        return orderSummary;
    }
}
