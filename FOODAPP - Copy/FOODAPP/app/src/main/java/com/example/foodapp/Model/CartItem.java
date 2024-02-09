package com.example.foodapp.Model;

import android.content.Context;

import com.example.foodapp.DatabaseHelper.DatabaseHelper;

public class CartItem  {
    private Food food;
    private int quantity;
    private int userId;
    private String status;
    int foodId;
    private String ngaydat;
    private String giodat;
    private float tongtien;
    DatabaseHelper databaseHelper;
    Context context;

    public CartItem(int userId, String status, int foodId, int quantity, String ngaydat, String giodat, float tongtien) {
        this.quantity = quantity;
        this.userId = userId;
        this.status = status;
        this.foodId = foodId;
        this.ngaydat = ngaydat;
        this.giodat = giodat;
        this.tongtien = tongtien;
        databaseHelper = new DatabaseHelper(context);
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getNgaydat() {
        return ngaydat;
    }

    public void setNgaydat(String ngaydat) {
        this.ngaydat = ngaydat;
    }

    public String getGiodat() {
        return giodat;
    }

    public void setGiodat(String giodat) {
        this.giodat = giodat;
    }

    public float getTongtien() {
        return tongtien;
    }

    public void setTongtien(float tongtien) {
        this.tongtien = tongtien;
    }
}
