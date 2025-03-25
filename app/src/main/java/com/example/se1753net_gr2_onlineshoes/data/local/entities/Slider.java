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

//    public Slider(int i, String s, String s1, String s2, int i1, int i2) {
//    }


    public Slider(@NonNull String sliderId, Date updatedAt, String notes, String status, String backlink, String imageUrl, String title, Date createdAt) {
        this.sliderId = sliderId;
        this.updatedAt = updatedAt;
        this.notes = notes;
        this.status = status;
        this.backlink = backlink;
        this.imageUrl = imageUrl;
        this.title = title;
        this.createdAt = createdAt;
    }
}
