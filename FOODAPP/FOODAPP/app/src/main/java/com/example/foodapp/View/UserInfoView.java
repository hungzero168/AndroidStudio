package com.example.foodapp.View;

import com.example.foodapp.Model.User;

import java.util.ArrayList;

public interface UserInfoView {
    void showUserInfo(User user, String text);
    void updateUserInfo(String username, String diaChi, String hoTen, String sdt);
    void showError(String message);
}

