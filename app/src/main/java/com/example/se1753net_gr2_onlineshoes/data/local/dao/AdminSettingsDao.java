package com.example.se1753net_gr2_onlineshoes.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.AdminSettings;

import java.util.List;

@Dao
public interface AdminSettingsDao {
    @Insert
    void insertSetting(AdminSettings setting);

    @Update
    void updateSetting(AdminSettings setting);

    @Delete
    void deleteSetting(AdminSettings setting);

    @Query("SELECT * FROM Admin_Settings WHERE setting_key = :key")
    AdminSettings getSettingByKey(String key);
}
