package com.example.se1753net_gr2_onlineshoes.viewmodel.marketing_viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.Order;
import com.example.se1753net_gr2_onlineshoes.data.repository.OrderRepository;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MarketingOrderListViewModel extends ViewModel {
    private final OrderRepository repository;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<List<Order>> orderListLiveData = new MutableLiveData<>();

    public MarketingOrderListViewModel(OrderRepository repository) {
        this.repository = repository;
        loadProducts();
    }

    public LiveData<List<Order>> getOrders() {
        return orderListLiveData;
    }

    private void loadProducts() {
        disposable.add(repository.getAllOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        orders -> {
                            Log.d("MarketingOrderListViewModel", "Orders fetched: " + orders.size());
                            for (Order order : orders) {
                                Log.d("MarketingOrderListViewModel", "Order: " + order.trackingNumber + ", Name: " + order.shippingAddress);
                            }
                            orderListLiveData.setValue(orders);
                        },
                        throwable -> {
                            Log.e("MarketingOrderListViewModel", "Error fetching Order", throwable);
                        }
                ));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear(); // Prevent memory leaks
    }
}
