package com.example.se1753net_gr2_onlineshoes.viewmodel.marketing_viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.Slider;
import com.example.se1753net_gr2_onlineshoes.data.repository.SliderRepository;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MarketingSliderListViewModel extends ViewModel {
    private final SliderRepository repository;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<List<Slider>> sliderListLiveData = new MutableLiveData<List<Slider>>();

    private final MutableLiveData<String> filterStatusLiveData = new MutableLiveData<>(); // Active, Inactive, or null (All)
    private final MutableLiveData<String> searchQuery = new MutableLiveData<>(); // Search term


    public MarketingSliderListViewModel(SliderRepository repository) {
        this.repository = repository;
        loadSliders();
    }

    public LiveData<List<Slider>> getSliderListLiveData() {
        return sliderListLiveData;
    }

    public void setFilterStatus(String status) {
        filterStatusLiveData.setValue(status);
        loadSliders(); // Reload the list based on filter
    }

    public void setSearchQuery(String query) {
        searchQuery.setValue(query);
        loadSliders(); // Reload with search
    }

    private void loadSliders() {
        disposable.add(repository.getAllSliders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sliders -> {
                            sliderListLiveData.setValue(filterSliders(sliders)); // Apply filtering
                        },
                        throwable -> Log.e("MarketingSliderListVM", "Error fetching sliders", throwable)
                ));
    }

    private List<Slider> filterSliders(List<Slider> sliders) {
        String statusFilter = filterStatusLiveData.getValue(); // Can be "Active", "Inactive", or null
        Log.e("MarketingSliderListVM", "Slider Filter type: " + statusFilter);
        String search = searchQuery.getValue() != null ? searchQuery.getValue().toLowerCase() : "";

        return sliders.stream()
                .filter(slider -> (statusFilter == null || slider.status.equalsIgnoreCase(statusFilter)) &&
                        (search.isEmpty() || slider.title.toLowerCase().contains(search)))
                .collect(Collectors.toList());
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear(); // Prevent memory leaks
    }
}
