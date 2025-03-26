package com.example.se1753net_gr2_onlineshoes.data.local.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

public class CartItemWithProduct {
    @Embedded
    public CartItem cartItem;

    @Relation(
            parentColumn = "product_id",
            entityColumn = "product_id"
    )
    public Product product;

}
