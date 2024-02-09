package com.example.foodapp.Presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.foodapp.DatabaseHelper.DatabaseHelper;
import com.example.foodapp.Fragment.HomeFragment;
import com.example.foodapp.Model.Food;
import com.example.foodapp.R;
import com.example.foodapp.View.FoodDetailActivity;
import com.example.foodapp.View.FoodView;
import com.example.foodapp.View.SearchView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FoodPresenter {
    private Food food;
    private FoodView view;
    SearchView viewSearch;
    private ArrayList<Food> foodList;

    public FoodPresenter(Food food, FoodView view) {
        this.food = food;
        this.view = view;
        this.foodList = fetchData();

    }
    public FoodPresenter(Food food, FoodView view, SearchView viewSearch) {
        this.food = food;
        this.view = view;
        this.viewSearch = viewSearch; // Initialize viewSearch here
        this.foodList = fetchData();
    }

    public void loadFoodList() {
        ArrayList<Food> foodList = fetchData();

        if (foodList != null) {
            view.showFoodList(foodList);
        } else {
            view.showError("Không thể tải danh sách món ăn");
        }
    }

    private ArrayList<Food> fetchData() {
        ArrayList<Food> foodList = new ArrayList<>();
        foodList = food.fetchData();

        return foodList;
    }

    public void loadCartFoodList() {
        ArrayList<Food> cartFoodList = food.fetchCartData();

        if (cartFoodList != null) {
            view.showCartFoodList(cartFoodList);
        } else {
            view.showError("Không thể tải danh sách món ăn");
        }
    }
    public void searchFood(String query) {
        ArrayList<Food> searchResults = new ArrayList<>();

        for (Food food : foodList) {
            if (food.getTenDoAn().toLowerCase().contains(query.toLowerCase())) {
                searchResults.add(food);
            }
        }

        viewSearch.showSearchResults(searchResults);
    }


    public void confirmOrder(float newTotalPrice) {
        boolean orderConfirmed = food.confirmOrder(newTotalPrice);

        if (orderConfirmed) {
            // Handle success (e.g., show a success message or update the UI)
            view.showOrderConfirmationSuccess();
        } else {
            // Handle failure (e.g., show an error message or update the UI accordingly)
            view.showOrderConfirmationFailure();
        }
    }



}
