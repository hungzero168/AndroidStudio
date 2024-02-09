package com.example.foodapp.Presenter;


import com.example.foodapp.Model.QuantityPickerModel;
import com.example.foodapp.View.QuantityPickerView;


public class QuantityPickerPresenter {
    private QuantityPickerModel model;
    private QuantityPickerView view;

    public QuantityPickerPresenter(QuantityPickerModel model, QuantityPickerView view) {
        this.model = model;
        this.view = view;
    }

    public void onMinusClicked() {
        int quantity = model.getQuantity();
        if (quantity > 1) {
            model.setQuantity(quantity - 1);
            view.updateQuantity(model.getQuantity());
        }
    }

    public void onPlusClicked() {
        int quantity = model.getQuantity();
        model.setQuantity(quantity + 1);
        view.updateQuantity(model.getQuantity());
    }

    public void onAddToCartClicked() {
        view.showQuantityPickerDialog(model.getQuantity());
        int selectedQuantity = model.getQuantity();
        view.showQuantityPickerDialog(selectedQuantity);
    }
}

