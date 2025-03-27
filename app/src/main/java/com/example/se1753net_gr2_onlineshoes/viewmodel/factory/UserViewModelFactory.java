package com.example.se1753net_gr2_onlineshoes.viewmodel.factory;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.se1753net_gr2_onlineshoes.data.repository.CustomerRepository;
import com.example.se1753net_gr2_onlineshoes.viewmodel.sale_viewmodel.UserViewModel;

import io.reactivex.rxjava3.annotations.NonNull;

public class UserViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;
    private final CustomerRepository userRepository;

    public UserViewModelFactory(Application application, CustomerRepository userRepository) {
        this.application = application;
        this.userRepository = userRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel(application, userRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}