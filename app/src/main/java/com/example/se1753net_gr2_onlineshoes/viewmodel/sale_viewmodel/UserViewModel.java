package com.example.se1753net_gr2_onlineshoes.viewmodel.sale_viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.User;
import com.example.se1753net_gr2_onlineshoes.data.repository.CustomerRepository;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

    public class UserViewModel extends AndroidViewModel {
        private final CustomerRepository userRepository;
        private final CompositeDisposable disposable = new CompositeDisposable();

        private final MutableLiveData<List<User>> customersList = new MutableLiveData<>();
        private final MutableLiveData<User> selectedCustomer = new MutableLiveData<>();
        private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

        public UserViewModel(Application application, CustomerRepository userRepository) {
            super(application);
            this.userRepository = userRepository;
        }

        public LiveData<List<User>> getCustomersList() {
            return customersList;
        }

        public LiveData<User> getSelectedCustomer() {
            return selectedCustomer;
        }

        public LiveData<Boolean> getIsLoading() {
            return isLoading;
        }


        // 🟢 Lấy danh sách tất cả khách hàng
        public void loadAllCustomers() {
            isLoading.setValue(true);
            disposable.add(userRepository.getAllCustomers()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(users -> {
                        customersList.setValue(users);
                        isLoading.setValue(false);
                    }, throwable -> {
                        isLoading.setValue(false);
                        Log.e("UserViewModel", "Error loading customers", throwable);
                    }));
        }

        // 🟢 Tìm kiếm khách hàng
        public void searchCustomers(String query) {
            isLoading.setValue(true);
            disposable.add(userRepository.searchCustomers(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(users -> {
                        customersList.setValue(users);
                        isLoading.setValue(false);
                    }, throwable -> {
                        isLoading.setValue(false);
                        Log.e("UserViewModel", "Error searching customers", throwable);
                    }));
        }

        // 🟢 Lấy thông tin chi tiết khách hàng
        public void loadCustomerById(String userId) {
            isLoading.setValue(true);
            disposable.add(userRepository.getCustomerById(userId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(user -> {
                        selectedCustomer.setValue(user);
                        isLoading.setValue(false);
                    }, throwable -> {
                        isLoading.setValue(false);
                        Log.e("UserViewModel", "Error loading customer details", throwable);
                    }));
        }

        // 🟢 Sắp xếp danh sách khách hàng
        public void sortCustomers(String sortBy) {
            isLoading.setValue(true);
            disposable.add(userRepository.sortCustomers(sortBy)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(users -> {
                        customersList.setValue(users);
                        isLoading.setValue(false);
                    }, throwable -> {
                        isLoading.setValue(false);
                        Log.e("UserViewModel", "Error sorting customers", throwable);
                    }));
        }

        // 🟢 Thêm hoặc cập nhật khách hàng
        public void saveCustomer(User user) {
            disposable.add(userRepository.insertOrUpdateCustomer(user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> Log.d("UserViewModel", "Customer saved successfully"),
                            throwable -> Log.e("UserViewModel", "Error saving customer", throwable)));
        }

        // 🟢 Xóa khách hàng
        public void deleteCustomer(User user) {
            disposable.add(userRepository.deleteCustomer(user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> Log.d("UserViewModel", "Customer deleted successfully"),
                            throwable -> Log.e("UserViewModel", "Error deleting customer", throwable)));
        }


        @Override
        protected void onCleared() {
            super.onCleared();
            disposable.clear();
        }
    }