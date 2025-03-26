package com.example.se1753net_gr2_onlineshoes.viewmodel.marketing_viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.Slider;
import com.example.se1753net_gr2_onlineshoes.data.repository.SliderRepository;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MarketingSliderListViewModel extends ViewModel {
    private final SliderRepository repository;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<List<Slider>> silderlistlivedata = new MutableLiveData<List<Slider>>();

    public MarketingSliderListViewModel(SliderRepository repository) {
        this.repository = repository;
        loadSliders();
    }

    public LiveData<List<Slider>> getsliderlistlivedata() {
        return silderlistlivedata;
    }

    private void loadSliders() {
        disposable.add(repository.getAllSliders() // Use the Single version for debugging
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sliders -> {
                            Log.d("MarketingSliderListVM", "Sliders fetched: " + sliders.size());
                            for (Slider slider : sliders) {
                                Log.d("MarketingSliderListVM", "Sliders: " + slider.sliderId + ", Name: " + slider.title);
                            }
                            if (sliders.isEmpty()) {
                                Log.w("MarketingSliderListVM", "Database is empty!");
                            }
                            silderlistlivedata.setValue(sliders);
                        },
                        throwable -> Log.e("MarketingSliderListVM", "Error fetching sliders", throwable)
                ));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear(); // Prevent memory leaks
    }
}
