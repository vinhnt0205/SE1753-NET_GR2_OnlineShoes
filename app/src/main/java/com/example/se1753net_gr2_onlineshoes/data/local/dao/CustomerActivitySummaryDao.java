package com.example.se1753net_gr2_onlineshoes.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.CustomerActivitySummary;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface CustomerActivitySummaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertOrUpdate(CustomerActivitySummary activitySummary);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertOrUpdateAll(List<CustomerActivitySummary> activitySummaries);

    @Query("SELECT * FROM Customer_Activity_Summary WHERE user_id = :userId")
    Flowable<List<CustomerActivitySummary>> getActivitiesByUser(String userId);

    @Query("SELECT * FROM Customer_Activity_Summary WHERE user_id = :userId AND activity_type = :activityType")
    Flowable<List<CustomerActivitySummary>> getActivitiesByUserAndType(String userId, String activityType);

    @Query("SELECT * FROM Customer_Activity_Summary WHERE user_id = :userId AND product_id = :productId")
    Single<CustomerActivitySummary> getActivityByUserAndProduct(String userId, String productId);

    @Delete
    Completable delete(CustomerActivitySummary activitySummary);

    @Query("DELETE FROM Customer_Activity_Summary WHERE user_id = :userId")
    Completable deleteByUser(String userId);
}
