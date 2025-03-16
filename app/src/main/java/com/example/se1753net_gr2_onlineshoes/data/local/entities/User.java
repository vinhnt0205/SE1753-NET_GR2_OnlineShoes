package com.example.se1753net_gr2_onlineshoes.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Users")
public class User {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "user_id")
    public String userId;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "password_hash")
    public String passwordHash;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "avatar_url")
    public String avatarUrl;

    @ColumnInfo(name = "created_at")
    public String createdAt;

    @ColumnInfo(name = "updated_at")
    public String updatedAt;

    @ColumnInfo(name = "role_id")
    public String roleId;
}

