package com.example.se1753net_gr2_onlineshoes.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.TypeConverters;

import com.example.se1753net_gr2_onlineshoes.data.local.utils.DateConverter;

import java.util.Date;

@Entity(tableName = "Shopping_Cart")
@TypeConverters(DateConverter.class) // Apply TypeConverter
public class ShoppingCart {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "cart_id")
    public String cartId;

    @NonNull
    @ColumnInfo(name = "user_id")
    public String userId;

    @ColumnInfo(name = "updated_at")
    public Date updatedAt;

    public ShoppingCart() {
    }

    @Ignore
    public ShoppingCart(String cartId, String userId, Date updatedAt) {
        this.cartId = cartId;
        this.userId = userId;
        this.updatedAt = updatedAt;
    }

    @NonNull
    public String getCartId() {
        return cartId;
    }

    public void setCartId(@NonNull String cartId) {
        this.cartId = cartId;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
