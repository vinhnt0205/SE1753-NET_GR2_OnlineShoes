package com.example.se1753net_gr2_onlineshoes.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "Feedback")
public class Feedback {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "feedback_id")
    public String feedbackId;

    @NonNull
    @ColumnInfo(name = "order_detail_id")
    public String orderDetailId;

    @NonNull
    @ColumnInfo(name = "user_id")
    public String userId;

    @NonNull
    @ColumnInfo(name = "rating")
    public int rating;

    @NonNull
    @ColumnInfo(name = "comment")
    public String comment;

    @ColumnInfo(name = "response")
    public String response;

    @NonNull
    @ColumnInfo(name = "created_at")
    public String createdAt;
}
