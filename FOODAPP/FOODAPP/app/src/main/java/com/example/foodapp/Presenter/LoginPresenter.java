package com.example.foodapp.Presenter;

import com.example.foodapp.Model.User;
import com.example.foodapp.View.LoginView;

public class LoginPresenter {
    private User user;
    private LoginView loginView;


    public LoginPresenter(User user, LoginView loginView) {
        this.user = user;
        this.loginView = loginView;
        //lay tu model ktra
    }


    public void LoginUser(String username, String password) {
        if (user.isLoginValid(username, password)) {
            loginView.onLoginSuccess();
        } else
            loginView.onLoginError();
    }
}
