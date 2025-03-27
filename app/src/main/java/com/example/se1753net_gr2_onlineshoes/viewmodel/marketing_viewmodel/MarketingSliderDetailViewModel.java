package com.example.se1753net_gr2_onlineshoes.viewmodel.marketing_viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.se1753net_gr2_onlineshoes.data.local.dao.SliderDao;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Slider;
import com.example.se1753net_gr2_onlineshoes.data.repository.SliderRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MarketingSliderDetailViewModel extends ViewModel {
    private final SliderRepository sliderRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<Slider> sliderLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isUpdating = new MutableLiveData<>(false);

    public MarketingSliderDetailViewModel(SliderRepository sliderRepository, String sliderId) {
        this.sliderRepository = sliderRepository;
        loadSliderDetails(sliderId);
    }

    public LiveData<Slider> getSliderLiveData() {
        return sliderLiveData;
    }

    public LiveData<Boolean> getIsUpdating() {
        return isUpdating;
    }

    // Load slider details from the database
    public void loadSliderDetails(String sliderId) {
        disposables.add(sliderRepository.getSliderById(sliderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sliderLiveData::setValue,
                        throwable -> Log.e("SliderViewModel", "Error loading slider", throwable)
                ));
    }

    // Update the slider in the database
    public void updateSlider(Slider updatedSlider) {
        isUpdating.setValue(true);
        disposables.add(sliderRepository.updateSlider(updatedSlider)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> isUpdating.setValue(false),
                        throwable -> {
                            isUpdating.setValue(false);
                            Log.e("SliderViewModel", "Error updating slider", throwable);
                        }
                ));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}

