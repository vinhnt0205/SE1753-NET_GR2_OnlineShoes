package com.example.se1753net_gr2_onlineshoes.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.TypeConverters;

import com.example.se1753net_gr2_onlineshoes.data.local.utils.DateConverter;

import java.util.Date;

@Entity(tableName = "Products")
@TypeConverters(DateConverter.class) // Apply TypeConverter
public class Product {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "product_id")
    public String productId;

    @NonNull
    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "description")
    public String description;

    @NonNull
    @ColumnInfo(name = "price")
    public Double price;

    @ColumnInfo(name = "sale_price")
    public Double salePrice;

    @NonNull
    @ColumnInfo(name = "stock")
    public int stock;

    @NonNull
    @ColumnInfo(name = "brand_id")
    public String brandId;

    @ColumnInfo(name = "is_featured")
    public Boolean isFeatured;

    @NonNull
    @ColumnInfo(name = "status")
    public String status;

    @NonNull
    @ColumnInfo(name = "created_at")
    public Date createdAt;

    @NonNull
    @ColumnInfo(name = "updated_at")
    public Date updatedAt;

    public Product(@NonNull String productId, @NonNull String name, String description, @NonNull Double price, Double salePrice, int stock, @NonNull String brandId, Boolean isFeatured, @NonNull String status, @NonNull Date createdAt, @NonNull Date updatedAt) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.salePrice = salePrice;
        this.stock = stock;
        this.brandId = brandId;
        this.isFeatured = isFeatured;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @NonNull
    public String getProductId() {
        return productId;
    }

    public void setProductId(@NonNull String productId) {
        this.productId = productId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}

