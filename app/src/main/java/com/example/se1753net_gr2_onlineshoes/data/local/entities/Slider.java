package com.example.se1753net_gr2_onlineshoes.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.TypeConverters;

import com.example.se1753net_gr2_onlineshoes.data.local.utils.DateConverter;

import java.util.Date;

@Entity(tableName = "Sliders")
@TypeConverters(DateConverter.class) // Apply TypeConverter
public class Slider {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "slider_id")
    public String sliderId;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "image_url")
    public String imageUrl;

    @ColumnInfo(name = "backlink")
    public String backlink;

    @ColumnInfo(name = "status")
    public String status; // ENUM('Active', 'Inactive')

    @ColumnInfo(name = "notes")
    public String notes;

    @ColumnInfo(name = "created_at")
    public Date createdAt;

    @ColumnInfo(name = "updated_at")
    public Date updatedAt;
}
