package com.example.se1753net_gr2_onlineshoes.data.local.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.Date;
import java.util.List;

public class ProductWithImages {


//    @Embedded
//    public Product product;
//
//    @Relation(parentColumn = "product_id", entityColumn = "product_id")
//    public List<ProductImage> images;

      public int product_id;

      public String name;

      public String description;

      public double price;

      public int stock;

      public double sale_price;

      public String image_url;

    public Date createdAt;

    public Date updatedAt;


}
