package com.example.se1753net_gr2_onlineshoes.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.se1753net_gr2_onlineshoes.data.local.entities.CartItem;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.CartItemWithProduct;

import java.util.List;

@Dao
public interface CartItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCartItem(CartItem cartItem);

    @Query("SELECT SUM(quantity) FROM cart_items")
    int getCartItemCount();


    @Update
    void updateCartItem(CartItem cartItem);

    @Delete
    void deleteCartItem(CartItem cartItem);

    @Query("SELECT * FROM Cart_Items WHERE cart_id = :cartId")
    List<CartItem> getCartItemsByCartId(String cartId);

    @Query("DELETE FROM Cart_Items WHERE cart_id = :cartId")
    void clearCart(String cartId);

    @Transaction
    @Query("SELECT * FROM Cart_Items WHERE cart_id = :cartId")
    List<CartItemWithProduct> getCartItemsWithProduct(String cartId);

    @Query("SELECT * FROM Cart_Items WHERE cart_id = :cartId AND product_id = :productId LIMIT 1")
    CartItem getCartItem(String cartId, String productId);
}

