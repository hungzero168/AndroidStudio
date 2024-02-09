package com.example.foodapp.Presenter;

import android.content.Context;

import com.example.foodapp.DatabaseHelper.DatabaseHelper;
import com.example.foodapp.Model.CartModel;
import com.example.foodapp.Model.Food;
import com.example.foodapp.Model.QuantityPickerModel;
import com.example.foodapp.View.FoodDetailView;
import com.example.foodapp.View.QuantityPickerView;

public class FoodDetailPresenter {
    private FoodDetailView view;
    private QuantityPickerView viewq;
    private DatabaseHelper databaseHelper;
    private Context context;
    Food model;
    private CartModel cartModel;
    QuantityPickerModel quantityPickerModel;

    public FoodDetailPresenter(FoodDetailView view, Context context) {
        this.view = view;
        this.context = context;
        this.model = new Food(context.getApplicationContext());
        this.databaseHelper = new DatabaseHelper(context);
        cartModel = new CartModel(context.getApplicationContext());
    }

    public FoodDetailPresenter(FoodDetailView view, CartModel cartModel) {
        this.view = view;
        this.cartModel  = cartModel;
    }
    public FoodDetailPresenter(FoodDetailView view, QuantityPickerModel quantityPickerModel) {
        this.view = view;
        this.quantityPickerModel = quantityPickerModel;
    }





    public void loadFoodDetail(int id) {
        Food food = model.LayThongTinDoAn(id); // Call the method from the model
        if (food != null) { // Check if the returned food object is not null
            view.showFoodDetail(food);
        } else {
            view.showError("Lỗi");
        }
    }



    public void addToCart(Food food, int quantity , String status, String ngaydat, String giodat, float tongtien) {
        // Call the addToCart method in the CartModel with all required parameters
        cartModel.addToCart(food, quantity, status, ngaydat, giodat, tongtien);

        view.showSuccessMessage("Thêm vào giỏ hàng thành công");

    }

}

