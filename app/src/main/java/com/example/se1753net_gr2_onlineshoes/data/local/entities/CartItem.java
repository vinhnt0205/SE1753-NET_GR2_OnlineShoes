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

    @ColumnInfo(name = "cart_id")
    public String cartId;

    @ColumnInfo(name = "product_id")
    public String productId;

    @ColumnInfo(name = "quantity")
    public int quantity;
}
