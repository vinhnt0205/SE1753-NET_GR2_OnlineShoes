package com.example.se1753net_gr2_onlineshoes.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "Admin_Settings")
public class AdminSettings {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "setting_id")
    public String settingId;

    @NonNull
    @ColumnInfo(name = "setting_key")
    public String settingKey;

    @NonNull
    @ColumnInfo(name = "setting_value")
    public String settingValue;
}

