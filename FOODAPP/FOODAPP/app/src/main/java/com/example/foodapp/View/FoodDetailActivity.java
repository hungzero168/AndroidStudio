package com.example.foodapp.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.foodapp.Adapter.FoodAdapter;
import com.example.foodapp.Adapter.CartItemAdapter;
import com.example.foodapp.Adapter.FoodAdapter;
import com.example.foodapp.Model.Food;
import com.example.foodapp.Model.QuantityPickerModel;
import com.example.foodapp.Presenter.FoodDetailPresenter;
import com.example.foodapp.Presenter.FoodPresenter;
import com.example.foodapp.Presenter.QuantityPickerPresenter;
import com.example.foodapp.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FoodDetailActivity extends AppCompatActivity implements FoodDetailView, QuantityPickerView  {
    private ImageView imgFoodDetail;
    private TextView txtFoodName, txtFoodPrice, txtFoodDescription, txtmota;

    private Toolbar toolbar;

    FoodAdapter adapter;

    private FoodDetailPresenter presenter;
    FoodPresenter presenterFood;
    private ArrayList<Food> foodList;
    private QuantityPickerPresenter presenterq;
    private int selectedQuantity;
    private Food selectedFood;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        // Ánh xạ các thành phần từ tệp giao diện
        imgFoodDetail = findViewById(R.id.imgFoodDetail);
        txtFoodName = findViewById(R.id.txtFoodName);
        txtFoodPrice = findViewById(R.id.txtFoodPrice);
        txtFoodDescription = findViewById(R.id.txtFoodDescription);
        txtmota = findViewById(R.id.txtmota);
        toolbar = findViewById(R.id.toolbar);


        foodList = new ArrayList<>();



        // Đặt Toolbar là action bar
        setSupportActionBar(toolbar);
        // Thêm nút giỏ hàng vào action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Hiển thị nút back
        // Đặt tiêu đề cho Toolbar
        getSupportActionBar().setTitle("Chi tiết món ăn");
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_shopping_cart_24); // Đặt biểu tượng giỏ hàng


        QuantityPickerModel quantityPickerModel = new QuantityPickerModel(this);
//        presenterq = new QuantityPickerPresenter(this, quantityPickerModel, this);



        int foodId = getIntent().getIntExtra("food_id",-1);

        presenter = new FoodDetailPresenter(this, this);
        presenter.loadFoodDetail(foodId);



    }

    @Override
    public void showFoodDetail(Food food) {
//        presenter = new FoodDetailPresenter(this, getApplicationContext());
//        int foodId = getIntent().getIntExtra("food_id", -1);
        txtFoodName.setText(food.getTenDoAn());
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedPrice = decimalFormat.format(food.getGiaDoAn());
        txtFoodPrice.setText("Giá: " + formattedPrice + "K");
        txtmota.setText(food.getMoTaDoAn());
        imgFoodDetail.setImageResource(food.getAnhDoAn());

        selectedFood = food;
    }

    @Override
    public void showError(String message) {
        // Hiển thị thông báo lỗi nếu cần
    }

    @Override
    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.food_detail_menu, menu);
        return true;
    }

    // Xử lý sự kiện khi menu item được chọn
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cart_item) {
            showQuantityPickerDialog(selectedQuantity);

            return true;
        } else if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void showQuantityPickerDialog(int initialQuantity) {

        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(R.layout.dialog_quantity_picker);

        ImageView imgFood = dialog.findViewById(R.id.imgFood);
        TextView txtFoodName = dialog.findViewById(R.id.txtFoodName);
        TextView txtTotalPrice = dialog.findViewById(R.id.txtTotalPrice);
        Button btnMinus = dialog.findViewById(R.id.btnMinus);
        TextView txtQuantity = dialog.findViewById(R.id.txtQuantity);
        Button btnPlus = dialog.findViewById(R.id.btnPlus);
        Button btnAddToCart = dialog.findViewById(R.id.btnAddToCart);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);



        txtFoodName.setText(selectedFood.getTenDoAn());
        txtTotalPrice.setText((int) (selectedFood.getGiaDoAn()) + "K");
        imgFood.setImageResource(selectedFood.getAnhDoAn());
        txtQuantity.setText(Integer.toString(initialQuantity));



        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(txtQuantity.getText().toString());
                // Tăng số lượng lên
                quantity++;
                txtTotalPrice.setText((int) (quantity * selectedFood.getGiaDoAn()) + "K");

                txtQuantity.setText(Integer.toString(quantity));
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(txtQuantity.getText().toString());
                // Giảm số lượng đi
                if (quantity > 1) {
                    quantity--;
                    txtTotalPrice.setText((int) (quantity * selectedFood.getGiaDoAn()) + "K");
                    txtQuantity.setText(Integer.toString(quantity));
                }
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedQuantity = Integer.parseInt(txtQuantity.getText().toString());
                int foodId = getIntent().getIntExtra("food_id", -1);

                String status = "giohang";
                Calendar calendar = Calendar.getInstance();
                Date currentDate = calendar.getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                String ngaydat = dateFormat.format(currentDate);
                String giodat = timeFormat.format(currentDate);
                // tổng tiền của 1 món ăn
                float tongtien = selectedQuantity * selectedFood.getGiaDoAn();
                if (tongtien == 0) {
                    Toast.makeText(FoodDetailActivity.this, "Số lượng lớn hơn hoặc bằng 1", Toast.LENGTH_SHORT).show();
                    return;
                }
                presenter.addToCart(selectedFood, selectedQuantity, status, ngaydat, giodat, tongtien);
//                finish();
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void updateQuantity(int quantity) {

    }

    @Override
    public void addToCart(int quantity) {

    }
}
