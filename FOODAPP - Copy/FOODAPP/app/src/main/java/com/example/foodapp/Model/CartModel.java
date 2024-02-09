package com.example.foodapp.Model;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.foodapp.DatabaseHelper.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class CartModel {
    private List<CartItem> cartItems;
    private Context context;
    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;


    public CartModel(Context context) {
        this.context = context;
        cartItems = new ArrayList<>();
        databaseHelper = new DatabaseHelper(context);
    }

    public void addToCart(Food food, int quantity, String status, String ngaydat, String giodat, float tongtien) {
        sharedPreferences = context.getSharedPreferences("dangnhap", MODE_PRIVATE);
        String user = sharedPreferences.getString("taikhoan", "");
        int userId = databaseHelper.getUserIdByUsername(user);
        insertCartItem(userId, status, food.getId(), quantity, ngaydat, giodat, tongtien);
    }


    public void insertCartItem(int userId, String status, int foodId, int quantity, String ngaydat, String giodat, float tongtien) {
        // Create a new Food object or use your existing Food object
        CartItem cartItem = new CartItem(userId, status, foodId, quantity, ngaydat, giodat, tongtien);

        // Add the cartItem to the list of cart items
        cartItems.add(cartItem);

        // Insert the cart item into the database
        long result = databaseHelper.updateOrInsertCartItem(userId, status, foodId, quantity, ngaydat, giodat, tongtien);

        if (result != -1) {
            // Insertion or update was successful
            // You can handle success here
        } else {
            // Insertion or update failed
            // You can handle failure here
        }
    }


}
