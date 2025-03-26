package com.example.se1753net_gr2_onlineshoes.viewmodel.sale_viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.OrderSummary;
import com.example.se1753net_gr2_onlineshoes.data.repository.OrderRepository;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SalesOverviewViewModel extends ViewModel {
    private final OrderRepository orderRepository;
    private final MutableLiveData<OrderSummary> orderSummary = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public SalesOverviewViewModel(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        loadOrderSummary();
    }

    private void loadOrderSummary() {
        compositeDisposable.add(
                orderRepository.getOrderSummary()
                        .subscribeOn(Schedulers.io())
                        .subscribe(orderSummary::postValue, throwable -> {
                            // Handle error (Log it or notify UI)
                        })
        );
    }

    public LiveData<OrderSummary> getOrderSummary() {
        return orderSummary;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
