package com.example.se1753net_gr2_onlineshoes.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.se1753net_gr2_onlineshoes.data.local.utils.DateConverter;

import java.util.Date;

@Entity(tableName = "Users")
@TypeConverters(DateConverter.class) // Apply TypeConverter
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
    public Date createdAt;

    @ColumnInfo(name = "updated_at")
    public Date updatedAt;

    @ColumnInfo(name = "role_id")
    public String roleId;

    public User(@NonNull String userId, String username, String passwordHash, String email, String avatarUrl, String roleId) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.roleId = roleId;
    }
}




