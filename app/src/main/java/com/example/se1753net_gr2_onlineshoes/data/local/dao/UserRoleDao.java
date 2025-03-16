package com.example.se1753net_gr2_onlineshoes.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.UserRole;

import java.util.List;

@Dao
public interface UserRoleDao {
    @Insert
    void insertRole(UserRole role);

    @Update
    void updateRole(UserRole role);

    @Delete
    void deleteRole(UserRole role);

    @Query("SELECT * FROM User_Roles WHERE role_id = :roleId")
    UserRole getRoleById(String roleId);

    @Query("SELECT * FROM User_Roles")
    List<UserRole> getAllRoles();
}

