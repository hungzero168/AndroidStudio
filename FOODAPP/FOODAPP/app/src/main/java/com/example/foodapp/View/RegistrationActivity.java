package com.example.foodapp.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodapp.Model.User;
import com.example.foodapp.Presenter.RegistrationPresenter;
import com.example.foodapp.R;

public class RegistrationActivity extends AppCompatActivity implements RegistrationView{

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button registerButton;

    private User user;
    private RegistrationPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        registerButton = findViewById(R.id.registerButton);

        user = new User(this);
        presenter = new RegistrationPresenter(user, this);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = usernameEditText.getText().toString();
                String passWord = passwordEditText.getText().toString();
                String confirmPass = confirmPasswordEditText.getText().toString();


                presenter.Registration(userName, passWord, confirmPass);
            }
        });



    }

    @Override
    public void onRegistrationSuccess() {
        // Display a success message
        Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
//        usernameEditText.setText("");
//        passwordEditText.setText("");
//        confirmPasswordEditText.setText("");
        finish();
    }

    @Override
    public void onRegistrationErro() {
        Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
    }
}