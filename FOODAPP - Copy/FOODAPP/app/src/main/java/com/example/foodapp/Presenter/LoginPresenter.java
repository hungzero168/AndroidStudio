package com.example.foodapp.Presenter;

import com.example.foodapp.Model.User;
import com.example.foodapp.View.LoginView;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginPresenter {
    private User user;
    private LoginView loginView;


    public LoginPresenter(User user, LoginView loginView) {
        this.user = user;
        this.loginView = loginView;
    }


//    public void LoginUser(String username, String password) {
//        if (user.isLoginValid(username, password)) {
//            loginView.onLoginSuccess();
//        } else
//            loginView.onLoginError();
//    }

    public void checkAccount(String taikhoan, String matkhau) {
        user.checkAccount(taikhoan, matkhau, new User.CheckAccountCallback() {
            @Override
            public void onAccountValid() {
                loginView.onLoginSuccess();
            }

            @Override
            public void onAccountInvalid() {
                loginView.onLoginError();
            }
        });
    }
}
