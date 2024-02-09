package com.example.foodapp.Model;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.foodapp.DatabaseHelper.DatabaseHelper;

public class QuantityPickerModel {
    private int quantity;
    private int foodId;
    Food food;
    Context context;
    SharedPreferences sharedPreferences;
    DatabaseHelper databaseHelper;

    public QuantityPickerModel(Context context) {
        this.context = context;
        this.quantity = 1;
        food = new Food(context);
        databaseHelper = new DatabaseHelper(context);
//        sharedPreferences = context.getSharedPreferences("dangnhap", Context.MODE_PRIVATE);
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;

    }
    public void setSelectedQuantity(int quantity, int foodId) {
        this.quantity = quantity;
        this.foodId = foodId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

}
