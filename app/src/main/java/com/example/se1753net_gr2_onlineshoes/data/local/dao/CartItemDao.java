package com.example.se1753net_gr2_onlineshoes.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.CartItem;

import java.util.List;

@Dao
public interface CartItemDao {
    @Insert
    void insertCartItem(CartItem cartItem);

    @Update
    void updateCartItem(CartItem cartItem);

    @Delete
    void deleteCartItem(CartItem cartItem);

    @Query("SELECT * FROM Cart_Items WHERE cart_id = :cartId")
    List<CartItem> getCartItemsByCartId(String cartId);

    @Query("DELETE FROM Cart_Items WHERE cart_id = :cartId")
    void clearCart(String cartId);
}

