package com.example.se1753net_gr2_onlineshoes.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.se1753net_gr2_onlineshoes.data.local.utils.DateConverter;

@Entity(tableName = "Users")
@TypeConverters(DateConverter.class) // Apply TypeConverter
public class User {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "user_id")
    public String userId;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "phoneNumber")
    public String phoneNumber;

    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "avatar_url")
    public String avatarUrl;

    @ColumnInfo(name = "role_id")
    public String roleId;

    @ColumnInfo(name = "password_hash")
    public String passwordHash;

    @ColumnInfo(name = "created_at", defaultValue = "0")
    public Long createdAt;   // ⚠️ Đổi thành INTEGER

    @ColumnInfo(name = "updated_at", defaultValue = "0")
    public Long updatedAt;   // ⚠️ Đổi thành INTEGER

    public User(@NonNull String userId, String username, String email, String phoneNumber, String address, String avatarUrl, String roleId, String passwordHash, Long createdAt, Long updatedAt) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.avatarUrl = avatarUrl;
        this.roleId = roleId;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public String getUsername() { return username; }
    public String getEmail() { return email; }

    public String getMobile() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getUserId() {
        return userId;
    }
}
