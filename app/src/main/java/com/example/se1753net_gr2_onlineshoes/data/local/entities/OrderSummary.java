package com.example.se1753net_gr2_onlineshoes.data.local.entities;

public class OrderSummary {
    public long totalOrders;
    public double totalRevenue;

    public OrderSummary(long totalOrders, double totalRevenue) {
        this.totalOrders = totalOrders;
        this.totalRevenue = totalRevenue;
    }

    public long getTotalOrders() {
        return totalOrders;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }
}
