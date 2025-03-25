package com.example.se1753net_gr2_onlineshoes.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.ShoppingCart;

import java.util.List;

@Dao
public interface ShoppingCartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCart(ShoppingCart cart);

    @Delete
    void deleteCart(ShoppingCart cart);

    @Query("SELECT * FROM Shopping_Cart WHERE user_id = :userId")
    ShoppingCart getCartByUser(String userId);




}
