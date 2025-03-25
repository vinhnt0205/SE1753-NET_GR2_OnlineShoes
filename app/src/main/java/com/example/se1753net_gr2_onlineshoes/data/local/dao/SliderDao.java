package com.example.se1753net_gr2_onlineshoes.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.Slider;

import java.util.List;

@Dao
public interface SliderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSlider(Slider slider);

    @Update
    void updateSlider(Slider slider);

    @Delete
    void deleteSlider(Slider slider);

    @Query("SELECT * FROM Sliders WHERE status = 'Active'")
    List<Slider> getActiveSliders();
//    LiveData<Slider> getActiveSliders();

}


