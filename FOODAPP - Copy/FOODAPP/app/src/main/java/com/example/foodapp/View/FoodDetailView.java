package com.example.foodapp.View;

import com.example.foodapp.Model.Food;

public interface FoodDetailView {
    void showFoodDetail(Food food);
    void showError(String message);
    void showSuccessMessage(String message);
}
