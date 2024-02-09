package com.example.foodapp.Presenter;

import android.content.Context;

import com.example.foodapp.DatabaseHelper.DatabaseHelper;
import com.example.foodapp.Model.User;
import com.example.foodapp.View.UserInfoView;

public class UserInfoPresenter {
    private UserInfoView view;
    private DatabaseHelper databaseHelper;

    public UserInfoPresenter(UserInfoView view, Context context) {
        this.view = view;
        databaseHelper = new DatabaseHelper(context);
    }


    public void showUserInfo(String username, String text) {
        User user = databaseHelper.getUserByUsername(username);
        if (user != null) {
            view.showUserInfo(user, text);
        } else {
            view.showError("Lối.");
        }
    }

    public Boolean updateUserInfor(String username, String diaChi, String hoTen, String sdt) {
        boolean update = databaseHelper.updateUser( username,  diaChi,  hoTen,  sdt);
        if (update) {
            view.updateUserInfo(username,diaChi,hoTen, sdt);
        } else {
            view.showError("lỗi");
        }
        return null;
    }
}

