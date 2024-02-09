package com.example.foodapp.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.foodapp.DatabaseHelper.DatabaseHelper;
import com.example.foodapp.Model.User;
import com.example.foodapp.Presenter.LoginPresenter;
import com.example.foodapp.R;
import com.example.foodapp.View.home.HomeActivity;

public class LoginActivity extends AppCompatActivity implements LoginView{

    private EditText edtten, edtPass;
    private Button btDangnhap, btDangki;
    private LoginPresenter loginPresenter;
    DatabaseHelper databaseHelper;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginview);

        databaseHelper = new DatabaseHelper(this);
        if (databaseHelper.isUserTableEmpty()) {
            databaseHelper.insertAcc("admin","123");
        }
        if (databaseHelper.isDoAnTableEmpty()) {
            databaseHelper.insertDoAn("Mì Quảng", 10, 8, "Mì Quảng ngon", R.drawable.fast_1);
            databaseHelper.insertDoAn("Bánh Mì", 15, 12, "Bánh Mì thơm ngon", R.drawable.fast_2);
            databaseHelper.insertDoAn("Bún Riêu Cua", 20, 18, "Bún Riêu Cua ngon", R.drawable.da1);
            databaseHelper.insertDoAn("Combo 2", 50, 18, "Bún Riêu Cua ngon", R.drawable.da2);
            databaseHelper.insertDoAn("Combo 1", 35, 18, "Bún Riêu Cua ngon", R.drawable.da3);
            databaseHelper.insertDoAn("Combo 3", 99, 18, "Bún Riêu Cua ngon", R.drawable.da4);
            databaseHelper.insertDoAn("Combo 4", 199, 18, "Bún Riêu Cua ngon", R.drawable.da5);
            databaseHelper.insertDoAn("Combo 5", 59, 18, "Bún Riêu Cua ngon", R.drawable.da6);
            databaseHelper.insertDoAn("Combo 6", 49, 18, "Bún Riêu Cua ngon", R.drawable.da7);
        }



        edtten = findViewById(R.id.edtten);
        edtPass = findViewById(R.id.edtPass);
        btDangnhap = findViewById(R.id.btDangnhap);
        btDangki = findViewById(R.id.btDangki);



        btDangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });




        User user = new User(this);
        loginPresenter = new LoginPresenter(user, this);

        sharedPreferences = getSharedPreferences("dangnhap", MODE_PRIVATE);
        editor = sharedPreferences.edit();




        btDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tk = edtten.getText().toString();
                String pw = edtPass.getText().toString();

                if (tk.equals("") || pw.equals("")) {
                    return;
                }

                loginPresenter.checkAccount(tk, pw);
                editor.putString("taikhoan", tk);
                editor.apply();
            }
        });
    }

    @Override
    public void onLoginSuccess() {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
    }

    @Override
    public void onLoginError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Lỗi Đăng Nhập");
        builder.setMessage("Đã xảy ra lỗi khi đăng nhập. Vui lòng thử lại sau.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}