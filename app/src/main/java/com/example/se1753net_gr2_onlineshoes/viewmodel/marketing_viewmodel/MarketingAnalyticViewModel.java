package com.example.se1753net_gr2_onlineshoes.viewmodel.marketing_viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.se1753net_gr2_onlineshoes.data.local.databaseview.ProductStatisticsView;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Order;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Product;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.ProductStatistics;
import com.example.se1753net_gr2_onlineshoes.data.repository.OrderRepository;
import com.example.se1753net_gr2_onlineshoes.data.repository.ProductRepository;
import com.example.se1753net_gr2_onlineshoes.data.repository.StatisticRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MarketingAnalyticViewModel extends ViewModel {
    private final OrderRepository orderRepository;
    private final StatisticRepository statisticRepository;
    private final ProductRepository productRepository;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<List<Order>> orderListLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<ProductStatisticsView>> productStatisticViewLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<ProductStatistics>> productStatisticLiveData = new MutableLiveData<>();

    private final List<Product> productList = new ArrayList<>();

    public MarketingAnalyticViewModel(OrderRepository orderRepository, StatisticRepository statisticRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.statisticRepository = statisticRepository;
        this.productRepository = productRepository;
        loadOrders();
        //loadProductStatisticViews();
        loadProductStatisticsByProductId("1");
    }

    public MutableLiveData<List<ProductStatistics>> getProductStatisticLiveData() {
        return productStatisticLiveData;
    }

    public List<Product> getProducts() {
        return productList;
    }

    public void loadProducts(Consumer<List<Product>> callback) {
        disposable.add(productRepository.getAllProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        products -> {
                            Log.d("MarketingAnalyticVM", "Products fetched: " + products.size());
                            callback.accept(products); // Use callback to return data
                        },
                        throwable -> {
                            Log.e("MarketingAnalyticVM", "Error fetching products", throwable);
                            callback.accept(Collections.emptyList()); // Return empty list on error
                        }
                ));
    }


    /*public void loadProducts() {
        disposable.add(productRepository.getAllProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        products -> {
                            Log.d("MarketingAnalyticVM", "Products fetched: " + products.size());
                            for (Product product : products) {
                                Log.d("MarketingAnalyticVM", "Product: " + product.brandId + ", Name: " + product.name);
                            }
                            productList.addAll(products);
                        },
                        throwable -> {
                            Log.e("MarketingAnalyticVM", "Error fetching products", throwable);
                        }
                ));
    }*/

    public LiveData<List<Order>> getOrders() {
        return orderListLiveData;
    }

    public void loadProductStatisticsByProductId(String productId) {
        disposable.add(statisticRepository.getStatisticsByProductId(productId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        productStatistics -> {
                            Log.d("MarketingAnalyticVM", "Product Statistic fetched: " + productStatistics.size());
                            for (ProductStatistics statistics : productStatistics) {
                                Log.d("MarketingAnalyticVM", "ProductId: " + statistics.productId + ", Total sold: " + statistics.totalSold);
                            }
                            productStatisticLiveData.setValue(productStatistics);
                        },
                        throwable -> {
                            Log.e("MarketingAnalyticVM", "Error fetching Order", throwable);
                        }
                ));
    }

    private void loadProductStatisticViews() {
        disposable.add(statisticRepository.getAllProductStatisticsViews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        productStatisticsViews -> {
                            Log.d("MarketingAnalyticVM", "Product Statistic fetched: " + productStatisticsViews.size());
                            for (ProductStatisticsView productStatisticsView : productStatisticsViews) {
                                Log.d("MarketingAnalyticVM", "ProductId: " + productStatisticsView.productId + ", Total sold: " + productStatisticsView.totalSold);
                            }
                            productStatisticViewLiveData.setValue(productStatisticsViews);
                        },
                        throwable -> {
                            Log.e("MarketingAnalyticVM", "Error fetching Order", throwable);
                        }
                ));
    }

    private void loadOrders() {
        disposable.add(orderRepository.getAllOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        orders -> {
                            Log.d("MarketingAnalyticVM", "Orders fetched: " + orders.size());
                            for (Order order : orders) {
                                Log.d("MarketingAnalyticVM", "Order: " + order.trackingNumber + ", Name: " + order.shippingAddress);
                            }
                            orderListLiveData.setValue(orders);
                        },
                        throwable -> {
                            Log.e("MarketingAnalyticVM", "Error fetching Order", throwable);
                        }
                ));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear(); // Prevent memory leaks
    }
}
