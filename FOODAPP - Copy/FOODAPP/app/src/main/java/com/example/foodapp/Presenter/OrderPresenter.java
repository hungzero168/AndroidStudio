package com.example.foodapp.Presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.foodapp.DatabaseHelper.DatabaseHelper;
import com.example.foodapp.Model.OrderInfo;
import com.example.foodapp.View.OrderView;

import java.util.List;

public class OrderPresenter {
    private OrderView view;
    private DatabaseHelper databaseHelper;
    private Context context;
    private SharedPreferences sharedPreferences;

    public OrderPresenter(OrderView view, Context context) {
        this.view = view;
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }


    public void loadOrders() {
        sharedPreferences = context.getSharedPreferences("dangnhap", context.MODE_PRIVATE);
        String id = sharedPreferences.getString("taikhoan", "");
        int userId = databaseHelper.getUserIdByUsername(id);
        List<OrderInfo> orderList = databaseHelper.getOrdersWithUserInfo(userId, "donhang");
        view.displayOrders(orderList);
    }
}

