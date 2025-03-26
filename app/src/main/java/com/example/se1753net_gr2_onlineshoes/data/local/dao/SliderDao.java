package com.example.se1753net_gr2_onlineshoes.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.Slider;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface SliderDao {

    @Insert
    Completable insertSlider(Slider slider);

    @Update
    Completable updateSlider(Slider slider);

    @Delete
    Completable deleteSlider(Slider slider);

    @Query("SELECT * FROM Sliders WHERE status = 'Active'")
    List<Slider> getActiveSliders();

    @Query("SELECT * FROM Sliders WHERE slider_id = :sliderId")
    Single<Slider> getSliderById(String sliderId);

    @Query("SELECT * FROM Sliders")
    Single<List<Slider>> getAllSliders();

    @Query("SELECT * FROM Sliders WHERE status = :status")
    Flowable<List<Slider>> getSlidersByStatus(String status);

    @Query("SELECT * FROM Sliders ORDER BY created_at DESC LIMIT :pageSize OFFSET :offset")
    List<Slider> getPagedSliders(int pageSize, int offset);

}

