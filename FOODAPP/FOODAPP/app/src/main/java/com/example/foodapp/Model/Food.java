package com.example.foodapp.Model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.foodapp.DatabaseHelper.DatabaseHelper;

import java.util.ArrayList;

public class Food {
    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;
    private Context context;
    private int id;
    private String tenDoAn;
    private float GiaDoAn;
    private float GiaKhuyenMai;
    private String MoTaDoAn;
    private int anhDoAn;
    private int soLuong;

    public Food(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
        sharedPreferences = context.getSharedPreferences("dangnhap", Context.MODE_PRIVATE);
    }
    public Food(String tenDoAn, float giaDoAn, float giaKhuyenMai, String moTaDoAn, int anhDoAn) {
        this.tenDoAn = tenDoAn;
        GiaDoAn = giaDoAn;
        GiaKhuyenMai = giaKhuyenMai;
        MoTaDoAn = moTaDoAn;
        this.anhDoAn = anhDoAn;
        this.databaseHelper = new DatabaseHelper(context);
    }
    public Food(int id, String tenDoAn, float giaDoAn, float giaKhuyenMai, String moTaDoAn, int anhDoAn) {
        this.id = id;
        this.tenDoAn = tenDoAn;
        GiaDoAn = giaDoAn;
        GiaKhuyenMai = giaKhuyenMai;
        MoTaDoAn = moTaDoAn;
        this.anhDoAn = anhDoAn;
        this.databaseHelper = new DatabaseHelper(context);
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getTenDoAn() {
        return tenDoAn;
    }

    public void setTenDoAn(String tenDoAn) {
        this.tenDoAn = tenDoAn;
    }

    public float getGiaDoAn() {
        return GiaDoAn;
    }

    public void setGiaDoAn(float giaDoAn) {
        GiaDoAn = giaDoAn;
    }

    public float getGiaKhuyenMai() {
        return GiaKhuyenMai;
    }

    public void setGiaKhuyenMai(float giaKhuyenMai) {
        GiaKhuyenMai = giaKhuyenMai;
    }

    public String getMoTaDoAn() {
        return MoTaDoAn;
    }

    public void setMoTaDoAn(String moTaDoAn) {
        MoTaDoAn = moTaDoAn;
    }

    public int getAnhDoAn() {
        return anhDoAn;
    }

    public void setAnhDoAn(int anhDoAn) {
        this.anhDoAn = anhDoAn;
    }


    public ArrayList<Food> fetchData() {
        ArrayList<Food> foodList = new ArrayList<>();
        foodList = databaseHelper.getAllDoAn();

        return foodList;
    }

    public Food LayThongTinDoAn(int idFood) {
        Food food = databaseHelper.getFoodById(idFood);
        return food;
    }



    public ArrayList<Food> fetchCartData() {
        sharedPreferences = context.getSharedPreferences("dangnhap", context.MODE_PRIVATE);
        String user = sharedPreferences.getString("taikhoan", "");
        int userId = databaseHelper.getUserIdByUsername(user);
        ArrayList<Food> foodList = databaseHelper.getCartDoAnByUser(userId);
        return foodList;
    }

    public boolean confirmOrder(float newTotalPrice, String selectedPaymentMethod) {
        sharedPreferences = context.getSharedPreferences("dangnhap", Context.MODE_PRIVATE);
        String id = sharedPreferences.getString("taikhoan", "");
        int userId = databaseHelper.getUserIdByUsername(id);
        String newStatus = "donhang";
        return databaseHelper.updateOrderStatusAndTotalPrice(userId, newStatus, newTotalPrice, selectedPaymentMethod);
    }


}
