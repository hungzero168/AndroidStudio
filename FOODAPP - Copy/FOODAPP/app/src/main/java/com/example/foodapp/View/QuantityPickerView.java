package com.example.foodapp.View;

public interface QuantityPickerView {
    void showQuantityPickerDialog(int initialQuantity);
    void updateQuantity(int quantity);
    void addToCart(int quantity);
}



