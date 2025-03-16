package com.example.se1753net_gr2_onlineshoes.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.Brand;

import java.util.List;

@Dao
public interface BrandDao {
    @Insert
    void insertBrand(Brand brand);

    @Update
    void updateBrand(Brand brand);

    @Delete
    void deleteBrand(Brand brand);

    @Query("SELECT * FROM Brands WHERE brand_id = :brandId")
    Brand getBrandById(String brandId);

    @Query("SELECT * FROM Brands")
    List<Brand> getAllBrands();
}

