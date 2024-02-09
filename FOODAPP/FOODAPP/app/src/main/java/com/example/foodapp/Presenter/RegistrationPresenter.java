package com.example.foodapp.Presenter;

import com.example.foodapp.Model.User;
import com.example.foodapp.View.RegistrationView;

public class RegistrationPresenter {
    private User user;
    private RegistrationView view;

    public RegistrationPresenter(User user, RegistrationView view) {
        this.user = user;
        this.view = view;
    }

    public void Registration(String username, String password1, String password2) {
        if (password1.equals(password2) && username != null && !username.contentEquals("Tên đăng nhập")) {
            if (user.registerUser(username, password1)) {
                view.onRegistrationSuccess();
            } else {
                view.onRegistrationErro();
            }
        } else {
            view.onRegistrationErro();
        }
    }
}

