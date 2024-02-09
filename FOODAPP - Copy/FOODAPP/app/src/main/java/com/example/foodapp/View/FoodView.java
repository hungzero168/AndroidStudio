package com.example.foodapp.View;

import com.example.foodapp.Model.Food;

import java.util.List;

public interface FoodView {
    void showFoodList(List<Food> foodList);
    void showError(String message);

    void showCartFoodList(List<Food> foodList);
    void showOrderConfirmationSuccess();
    void showOrderConfirmationFailure();
}
