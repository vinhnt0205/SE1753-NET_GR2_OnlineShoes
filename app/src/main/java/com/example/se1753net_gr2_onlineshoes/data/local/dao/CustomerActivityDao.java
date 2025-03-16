package com.example.se1753net_gr2_onlineshoes.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.CustomerActivity;

import java.util.List;

@Dao
public interface CustomerActivityDao {
    @Insert
    void insertActivity(CustomerActivity activity);

    @Query("SELECT * FROM Customer_Activity WHERE user_id = :userId ORDER BY created_at DESC")
    List<CustomerActivity> getActivitiesByUser(String userId);

    @Query("SELECT * FROM Customer_Activity WHERE product_id = :productId ORDER BY created_at DESC")
    List<CustomerActivity> getActivitiesByProduct(String productId);
}

