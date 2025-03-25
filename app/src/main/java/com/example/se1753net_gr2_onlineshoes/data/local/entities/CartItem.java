package com.example.se1753net_gr2_onlineshoes.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
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

    @Ignore
    private double price; // Giá sản phẩm (Cần load từ DB)

    public CartItem(@NonNull String cartItemId, @NonNull String cartId, @NonNull String productId, int quantity) {
        this.cartItemId = cartItemId;
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
    }

    @NonNull
    public String getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(@NonNull String cartItemId) {
        this.cartItemId = cartItemId;
    }

    @NonNull
    public String getCartId() {
        return cartId;
    }

    public void setCartId(@NonNull String cartId) {
        this.cartId = cartId;
    }

    @NonNull
    public String getProductId() {
        return productId;
    }

    public void setProductId(@NonNull String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalCost() {
        return price * quantity;
    }
}

