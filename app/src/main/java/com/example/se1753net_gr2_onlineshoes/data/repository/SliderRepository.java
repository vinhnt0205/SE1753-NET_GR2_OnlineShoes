package com.example.se1753net_gr2_onlineshoes.data.repository;

import android.app.Application;

import com.example.se1753net_gr2_onlineshoes.data.local.dao.SliderDao;
import com.example.se1753net_gr2_onlineshoes.data.local.database.ShoeShopDatabase;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Slider;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SliderRepository {

    private final SliderDao sliderDao;

    public SliderRepository(Application application) {
        ShoeShopDatabase database = ShoeShopDatabase.getInstance(application);
        this.sliderDao = database.sliderDao();
    }

    public SliderRepository(SliderDao sliderDao) {
        this.sliderDao = sliderDao;
    }

    public Completable insertSlider(Slider slider) {
        return sliderDao.insertSlider(slider)
                .subscribeOn(Schedulers.io());
    }

    public Completable updateSlider(Slider slider) {
        return sliderDao.updateSlider(slider)
                .subscribeOn(Schedulers.io());
    }

    public Completable deleteSlider(Slider slider) {
        return sliderDao.deleteSlider(slider)
                .subscribeOn(Schedulers.io());
    }

    public Single<Slider> getSliderById(String sliderId) {
        return sliderDao.getSliderById(sliderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<Slider>> getAllSliders() {
        return sliderDao.getAllSliders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<List<Slider>> getSlidersByStatus(String status) {
        return sliderDao.getSlidersByStatus(status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
