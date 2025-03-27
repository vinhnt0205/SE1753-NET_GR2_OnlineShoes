package com.example.se1753net_gr2_onlineshoes.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.User;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM Users WHERE user_id = :userId")
    User getUserById(String userId);

    @Query("SELECT * FROM Users WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);

    @Query("SELECT * FROM Users WHERE username = :username LIMIT 1")
    User getUserByUsername(String username);

    @Query("SELECT * FROM Users")
    List<User> getAllUsers();
    @Query("SELECT COUNT(*) FROM Users WHERE email = :email")
    int checkEmailExists(String email);
    @Query("UPDATE Users SET password_hash = :newPassword, updated_at = :updatedAt WHERE user_id = :userId")
    void updatePassword(String userId, String newPassword, Date updatedAt);

        @Query("SELECT * FROM Users WHERE role_id = 'customer' ORDER BY username ASC")
        Single<List<User>> getAllCustomers();

        @Query("SELECT * FROM Users WHERE role_id = 'customer' AND (username LIKE '%' || :query || '%' OR email LIKE '%' || :query || '%') ORDER BY username ASC")
        Single<List<User>> searchCustomers(String query);
    }



