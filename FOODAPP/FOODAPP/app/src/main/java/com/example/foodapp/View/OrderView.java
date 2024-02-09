package com.example.foodapp.View;

import com.example.foodapp.Model.OrderInfo;

import java.util.List;

public interface OrderView {
    void displayOrders(List<OrderInfo> orderList);
}
