package com.example.foodapp.Model;

import static com.example.foodapp.Api.ApiService.retrofit;

import android.content.Context;
import android.util.Log;

import com.example.foodapp.Api.ApiService;
import com.example.foodapp.DatabaseHelper.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

//    public boolean isLoginValid(String username, String password) {
//        Boolean check = databaseHelper.isLoginValid(username, password);
//
//        return check;
//    }

    public void checkAccount(String taikhoan, String matkhau, final CheckAccountCallback callback) {
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<User>> call = apiService.getUser(taikhoan, matkhau);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> users = response.body();
                    for (User user : users) {
                        if (user.getTaikhoan().equals(taikhoan) && user.getMatkhau().equals(matkhau)) {
                            callback.onAccountValid();
                            return;
                        }
                    }
                    // If the loop doesn't find a valid account, call onAccountInvalid
                    callback.onAccountInvalid();
                } else {
                    callback.onAccountInvalid();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                callback.onAccountInvalid();
            }
        });
    }



    public interface CheckAccountCallback {
        void onAccountValid();
        void onAccountInvalid();
    }


    public void hienThiThongtin(String user) {
        databaseHelper.getUserIdByUsername(user);
    }
}
