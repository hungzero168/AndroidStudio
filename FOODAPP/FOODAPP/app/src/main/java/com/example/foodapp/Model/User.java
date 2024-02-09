package com.example.foodapp.Model;

import android.content.Context;

import com.example.foodapp.DatabaseHelper.DatabaseHelper;

import java.util.ArrayList;

public class User {
    int id;
    private String taikhoan;
    private String matkhau,diaChi, hoTen, sdt;

    private DatabaseHelper databaseHelper;
    private Context context;

    public User(String taikhoan, String matkhau,Context context) {
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
        databaseHelper = new DatabaseHelper(context);
    }

    public User(int id, String taikhoan, String matkhau, String diaChi, String hoTen, String sdt) {
        this.id = id;
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
        this.diaChi = diaChi;
        this.hoTen = hoTen;
        this.sdt = sdt;
        databaseHelper = new DatabaseHelper(context);
    }

    public User(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    public User() {
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public boolean isLoginValid(String username, String password) {
        Boolean check = databaseHelper.isLoginValid(username, password);

        return check;
    }

    public boolean registerUser(String username, String password) {
        return databaseHelper.insertAcc(username,password);
    }
    public void hienThiThongtin(String user) {
        databaseHelper.getUserIdByUsername(user);
    }
}
