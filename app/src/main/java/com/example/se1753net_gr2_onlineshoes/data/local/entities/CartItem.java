package com.example.se1753net_gr2_onlineshoes.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;


@Entity(tableName = "Cart_Items")
public class CartItem {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "cart_item_id")
    public String cartItemId;

    @NonNull
    @ColumnInfo(name = "cart_id")
    public String cartId;

    @NonNull
    @ColumnInfo(name = "product_id")
    public String productId;

    @NonNull
    @ColumnInfo(name = "quantity")
    public int quantity;
}

