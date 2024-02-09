package com.example.foodapp.Fragment;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.Adapter.CartItemAdapter;
import com.example.foodapp.Adapter.FoodAdapter;
import com.example.foodapp.Model.Food;
import com.example.foodapp.Model.User;
import com.example.foodapp.Presenter.FoodPresenter;
import com.example.foodapp.Presenter.QuantityPickerPresenter;
import com.example.foodapp.Presenter.UserInfoPresenter;
import com.example.foodapp.R;
import com.example.foodapp.View.FoodView;
import com.example.foodapp.View.QuantityPickerView;
import com.example.foodapp.View.UserInfoView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GioHangFragment extends Fragment  implements FoodView, QuantityPickerView, UserInfoView {

    private ListView cartListView;
    private TextView totalPriceTextView;
    private Button checkoutButton;

    private FoodPresenter presenter;
    private QuantityPickerPresenter presenterq;
    private UserInfoPresenter presenterUser;
    private CartItemAdapter adapter;
    SharedPreferences sharedPreferences;
    private List<Food> foodList;
    private User currentUser;
    String userDangNhap;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gio_hang, container, false);

        cartListView = view.findViewById(R.id.cartListView);
        totalPriceTextView = view.findViewById(R.id.totalPriceTextView);
        checkoutButton = view.findViewById(R.id.checkoutButton);


        sharedPreferences = getContext().getSharedPreferences("dangnhap", Context.MODE_PRIVATE);
        userDangNhap = sharedPreferences.getString("taikhoan", "");
        presenterUser = new UserInfoPresenter(this, getContext());
        presenter = new FoodPresenter(new Food(getContext()), this);
        presenter.loadCartFoodList();
        cartListView.setAdapter(adapter);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cartItemsText = "";
                DecimalFormat decimalFormat = new DecimalFormat("0.##");
                for (Food food : foodList) {
                    String formattedPrice = decimalFormat.format(food.getGiaDoAn());
                    cartItemsText += "- " + food.getTenDoAn() + " (" + formattedPrice + "K) - Số lượng: " + food.getSoLuong() + "\n";
                }


                presenterUser.showUserInfo(userDangNhap,cartItemsText);

            }
        });
        return view;
    }

    @Override
    public void showFoodList(List<Food> foodList) {

    }




    @Override
    public void showError(String message) {

    }




    @Override
    public void showCartFoodList(List<Food> foodList) {

        this.foodList = new ArrayList<>(foodList);

        if (adapter == null) {
            ArrayList<Food> foodArrayList = new ArrayList<>(foodList);
            adapter = new CartItemAdapter(getContext(), foodArrayList);
            cartListView.setAdapter(adapter);
        } else {
            adapter.updateData(foodList);
        }

        float totalPrice = calculateTotalPrice(foodList);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedPrice = decimalFormat.format(totalPrice);
        totalPriceTextView.setText(String.format(Locale.getDefault(), "Tổng: %sK", formattedPrice));
    }



    @Override
    public void showOrderConfirmationSuccess(String selectedPaymentMethod) {
        float totalPrice = calculateTotalPrice(foodList);
        presenter.confirmOrder(totalPrice, selectedPaymentMethod);

        foodList.clear();

        adapter.updateData(new ArrayList<>());

        adapter.notifyDataSetChanged();


    }

    @Override
    public void showOrderConfirmationFailure() {

    }


    private float calculateTotalPrice(List<Food> foodList) {
        float total = 0;
        for (Food food : foodList) {
            total += food.getGiaDoAn() * food.getSoLuong();
        }
        return total;
    }



    @Override
    public void addToCart(int quantity) {

    }


    @Override
    public void showUserInfo(User user, String cartItemsText) {
        this.currentUser = user;

        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(R.layout.diolog_thanh_toan);

        TextView txttongtien = dialog.findViewById(R.id.tongtien);
        TextView txttenmon = dialog.findViewById(R.id.txttenmon);
        Spinner paymentMethodSpinner = dialog.findViewById(R.id.paymentMethodSpinner);
        EditText edtten = dialog.findViewById(R.id.edtten);
        EditText edtdiachi = dialog.findViewById(R.id.edtdiachi);
        EditText edtsdt = dialog.findViewById(R.id.edtsdt);
        Button btnxacnhan = dialog.findViewById(R.id.btnxacnhan);

        txttenmon.setText(cartItemsText);

        float totalPrice = calculateTotalPrice(foodList);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedPrice = decimalFormat.format(totalPrice);
        txttongtien.setText(formattedPrice+"K");


        String[] paymentMethods = {"Tiền mặt", "Thẻ tín dụng"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, paymentMethods);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final String[] selectedMethod = new String[1];

        paymentMethodSpinner.setAdapter(adapter);
        paymentMethodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("PaymentMethodSelected", "Selected position: " + position);
                selectedMethod[0] = paymentMethods[position];
                Log.d("PaymentMethodSelected", "selectedMethod[0] " + selectedMethod[0]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edtten.setText(user.getHoTen());
        edtdiachi.setText(user.getDiaChi());
        edtsdt.setText(user.getSdt());

        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newHoTen = edtten.getText().toString();
                String newDiaChi = edtdiachi.getText().toString();
                String newSdt = edtsdt.getText().toString();
                if (newHoTen != null && !newHoTen.isEmpty() || newDiaChi != null && !newDiaChi.isEmpty() || newSdt != null && !newSdt.isEmpty()) {
                    if (totalPrice == 0) {
                        Toast.makeText(getContext(), "Hãy thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    updateUserInfo(userDangNhap, newDiaChi, newHoTen, newSdt);
                    showOrderConfirmationSuccess(selectedMethod[0]);
                    foodList.clear();
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    if (newHoTen != null && !newHoTen.isEmpty()) edtten.setError("lỗi");
                    if (newDiaChi != null && !newDiaChi.isEmpty()) edtdiachi.setError("lỗi");
                    if (newSdt != null && !newSdt.isEmpty()) edtsdt.setError("lỗi");
                }





            }
        });


        dialog.show();
    }

    @Override
    public void updateUserInfo(String username, String diaChi, String hoTen, String sdt) {
        new UpdateUserInfoTask().execute(username, diaChi, hoTen, sdt);
    }

    private class UpdateUserInfoTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            if (params.length < 4) {
                return false;
            }

            String username = params[0];
            String diaChi = params[1];
            String hoTen = params[2];
            String sdt = params[3];

            if (presenterUser != null) {
                Boolean updateResult = presenterUser.updateUserInfor(username, diaChi, hoTen, sdt);

                if (updateResult != null) {
                    return updateResult;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

    }

    @Override
    public void showQuantityPickerDialog(int initialQuantity) {
    }

    @Override
    public void updateQuantity(int quantity) {

    }

}