package com.example.se1753net_gr2_onlineshoes.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "User_Roles")
public class UserRole {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "role_id")
    public String roleId;

    @ColumnInfo(name = "role_name")
    public String roleName;
}