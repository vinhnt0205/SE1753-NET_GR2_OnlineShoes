package com.example.se1753net_gr2_onlineshoes.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.Feedback;

import java.util.List;

@Dao
public interface FeedbackDao {
    @Insert
    void insertFeedback(Feedback feedback);

    @Update
    void updateFeedback(Feedback feedback);

    @Delete
    void deleteFeedback(Feedback feedback);

    @Query("SELECT * FROM Feedback WHERE user_id = :userId")
    List<Feedback> getFeedbackByUser(String userId);

    @Query("SELECT * FROM Feedback WHERE order_detail_id = :orderDetailId")
    List<Feedback> getFeedbackByOrderDetail(String orderDetailId);
}
