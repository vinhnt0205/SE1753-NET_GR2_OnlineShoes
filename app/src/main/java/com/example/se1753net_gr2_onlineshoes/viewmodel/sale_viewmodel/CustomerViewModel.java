package com.example.se1753net_gr2_onlineshoes.viewmodel.sale_viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.User;
import com.example.se1753net_gr2_onlineshoes.data.repository.CustomerRepository;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CustomerViewModel extends ViewModel {

    private final CustomerRepository repository;
    private final MutableLiveData<List<User>> customers = new MutableLiveData<>();
    private final CompositeDisposable disposable = new CompositeDisposable();

    public CustomerViewModel(CustomerRepository repository) {
        this.repository = repository;
        loadCustomers();
    }

    public LiveData<List<User>> getCustomers() {
        return customers;
    }

    private void loadCustomers() {
        disposable.add(repository.getAllCustomers()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(customers::postValue, Throwable::printStackTrace));
    }

    public void searchCustomers(String query) {
        disposable.add(repository.searchCustomers(query)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(customers::postValue, Throwable::printStackTrace));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}