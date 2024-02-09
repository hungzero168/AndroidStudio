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
//        if (databaseHelper.isUserTableEmpty()) {
//            databaseHelper.insertAcc("admin","123");
//        }
        if (databaseHelper.isDoAnTableEmpty()) {
            databaseHelper.insertDoAn("Mì Quảng", 55, 50, "Mì Quảng là một món ăn truyền thống đặc biệt của miền Trung Việt Nam. Mì Quảng có nguồn gốc từ Quảng Nam, thường được làm từ mì sợi ngắn và nguyên liệu như tôm, thịt gà, thịt heo, rau sống, gia vị và nước dùng đậm đà. Mì Quảng thường có màu sắc tươi sáng và hấp dẫn với hương vị đặc trưng. Món ăn này thường được ăn kèm với bánh tráng và rau sống. Mì Quảng là một phần quan trọng của ẩm thực đặc biệt và đa dạng của Việt Nam.", R.drawable.miquang);
            databaseHelper.insertDoAn("Bánh Mì", 20, 15, "Bánh Mì là một loại bánh truyền thống phổ biến tại Việt Nam và nhiều quốc gia khác. Đây là một loại bánh mỳ mềm, béo, có vỏ ngoài giòn, thường được làm từ bột mỳ, nước, men nở và một chút muối. Bánh Mì có nhiều biến thể, từ bánh mì sandwich phổ biến đến các loại bánh mì hình thù độc đáo, như bánh mì baguette Pháp hoặc bánh mì bánh mì cuốn. Nó thường được chế biến với nhiều loại nhân như thịt, cá, rau sống và gia vị, tạo nên hương vị đa dạng và hấp dẫn.", R.drawable.banhmy);
            databaseHelper.insertDoAn("Bún Riêu Cua", 20, 18, "Bún riêu cua là một món ăn ngon và phổ biến của ẩm thực Việt Nam. Món này thường bao gồm bún (mì sợi) được kết hợp với nước dùng làm từ cua, cà chua, mềm lòng đỏ trứng cua, và thường được thêm các loại thịt như thịt heo, tàu hủ ky, rau sống và gia vị. Bún riêu cua có hương vị đặc trưng, ngọt ngon và thanh mát. Món này thường được ăn kèm với rau sống, bún, và một chút nước mắm pha ngọt. Mìnđọc thêm về mì quảng trên trang web này . Đây là một món ăn phổ biến trong nhiều nhà hàng và quán ăn trên khắp Việt Nam.", R.drawable.bunrieucua);
            databaseHelper.insertDoAn("Phở bò tái", 35, 18, "Phở là một món súp mì sợi dài, thường ăn sáng hoặc bất kỳ thời điểm nào trong ngày. Nước dùng được nấu từ xương gà hoặc bò kết hợp với gia vị và thường được ăn kèm với thịt gà hoặc bò, bún, và rau sống.", R.drawable.phobotai);
            databaseHelper.insertDoAn("Bánh mì sandwich", 30, 18, "Bánh mì sandwich Việt Nam thường là bánh mì baguette với nhân thịt hoặc các loại nhân như chả lụa, thịt heo quay, thịt gà, và rau sống. Nó thường được ăn với sốt mù tạt và gia vị.", R.drawable.sandwichkepthit);
            databaseHelper.insertDoAn("Cơm tấm", 50, 18, "Cơm tấm là món cơm trắng thường kèm theo các loại nhân như sườn nướng, bì, chả lụa, thịt gà, trứng ốp la, và sốt nước mắm. Món ăn này thường được ăn kèm với rau sống và bánh tráng.", R.drawable.comtamjpg);
            databaseHelper.insertDoAn("Gỏi cuốn", 30, 18, "Gỏi cuốn là món ăn cuốn bằng bánh tráng, chứa các loại nhân như tôm, thịt, rau sống và bún, thường được ăn kèm với nước mắm pha ngọt.", R.drawable.goicuon);
            databaseHelper.insertDoAn("Bánh xèo", 35, 18, "Bánh xèo là một loại bánh mỳ mỏng, giòn, chứa nhân như tôm, thịt, gia vị và rau sống. Nó thường được cuốn trong lá bánh xèo và ăn kèm với nước mắm pha ngọt.", R.drawable.banhxeo);
            databaseHelper.insertDoAn("Bánh tráng trộn", 35, 18, "Một món tráng miệng phổ biến, bánh tráng trộn gồm các loại bánh tráng xé nhỏ, gia vị, rau sống, tôm, thịt, và nước mắm pha ngọt.", R.drawable.banhttrangtron);
        }



        edtten = findViewById(R.id.edtten);
        edtPass = findViewById(R.id.edtPass);
        btDangnhap = findViewById(R.id.btDangnhap);
        btDangki = findViewById(R.id.btDangki);



        btDangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
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

//                if (tk.equals("") || pw.equals("")) {
//                    return;
//                }
                //chuyen cho pre
                loginPresenter.LoginUser(tk, pw);
                editor.putString("taikhoan", tk);
                editor.apply();
            }
        });
    }

    @Override
    public void onLoginSuccess() {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
    } //interface corect -> mh

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